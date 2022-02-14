package kr.co.greetech.back.business.file;

import kr.co.greetech.back.business.datalogger.repository.DataLoggerRepository;
import kr.co.greetech.back.business.login.jwt.repository.CompanyRepository;
import kr.co.greetech.back.dto.MeasureFileDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.MeasureFile;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FileService {
    private String rootPath;
    private final FileRepository fileRepository;
    private final FileQueryRepository fileQueryRepository;
    private final CompanyRepository companyRepository;
    private final DataLoggerRepository dataLoggerRepository;

    public FileService(FileRepository fileRepository, FileQueryRepository fileQueryRepository, CompanyRepository companyRepository, DataLoggerRepository dataLoggerRepository) throws IOException {
        this.rootPath = "./uploads";
        this.fileRepository = fileRepository;
        this.fileQueryRepository = fileQueryRepository;
        this.companyRepository = companyRepository;
        this.dataLoggerRepository = dataLoggerRepository;

        createRootDirectory();
    }

    private Path getRootPath() {
        return Paths.get(rootPath).toAbsolutePath().normalize();
    }

    private Path getPath(String path) {
        return Paths.get(rootPath + "/" + path).toAbsolutePath().normalize();
    }

    private void createRootDirectory() throws IOException {
        Path fileLocation = getRootPath();
        Files.createDirectories(fileLocation);
    }

    private void createDirectory(String path) throws IOException {
        Path fileLocation = getPath(path);
        Files.createDirectories(fileLocation);
    }

    public void upload(String id, Long dataLoggerId, MeasureFileType type, MultipartFile file) throws FileUploadException {
        String fileName = getFileName(id, dataLoggerId, file);
        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/file")
//                .path("/" + id)
                .path("/" + dataLoggerId.toString())
                .path("/" + fileName)
                .toUriString();

        if (fileRepository.findByUrl(url).isPresent())
            throw new UsernameNotFoundException("Unauthorized");
        Company company = companyRepository.findByLoginId(id)
                .orElseThrow(() -> new UsernameNotFoundException("Unauthorized"));
        DataLogger dataLogger = dataLoggerRepository.findById(dataLoggerId)
                .orElseThrow(() -> new UsernameNotFoundException("Unauthorized"));

        MeasureFile measureFile = MeasureFile.create(url, type, company, dataLogger);
        fileRepository.save(measureFile);
    }

    public Resource download(String id, Long dataLoggerId, String fileName) throws MalformedURLException {
        Path filePath = getRootPath().resolve(id).resolve(dataLoggerId.toString()).resolve(fileName).normalize();
        return new UrlResource(filePath.toUri());
    }

    public String getFileName(String id, Long dataLoggerId, MultipartFile file) throws FileUploadException {
        String fileName = getFileName();

        try {
            createDirectory(id + "/" + dataLoggerId);
            Path targetLocation = getRootPath().resolve(id).resolve(dataLoggerId.toString()).resolve(fileName).normalize();
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch(Exception e) {
            throw new FileUploadException("파일 업로드에 실패하였습니다. 다시 시도하십시오.", e);
        }
    }

    public String getFileName() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return sdf.format(date) + ".csv";
    }

    public List<MeasureFileDto> select(String id, Long dataLoggerId, MeasureFileType type) {
        Company company = companyRepository.findByLoginId(id)
                .orElseThrow(() -> new UsernameNotFoundException("Unauthorized"));
        return fileQueryRepository.select(company.getId(), dataLoggerId, type);
    }

    public List<MeasureFileDto> selectAll(String id, Long dataLoggerId) {
        Company company = companyRepository.findByLoginId(id)
                .orElseThrow(() -> new UsernameNotFoundException("Unauthorized"));
        return fileQueryRepository.selectAll(company.getId(), dataLoggerId);
    }
}
