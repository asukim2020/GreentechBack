package kr.co.greetech.back.business.file;

import kr.co.greetech.back.business.login.jwt.util.JwtTokenUtil;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.dto.MeasureFileDto;
import kr.co.greetech.back.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@Profile("local")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    private final JwtTokenUtil jwtTokenUtil;
    private final FileService fileService;


    @PostMapping
    public void upload(
            @RequestParam MultipartFile file,
            @RequestParam Long dataLoggerId,
            @RequestParam(required=false) String type,
            HttpServletRequest request
    ) throws FileUploadException {
        String id = getUserName(request);
        MeasureFileType fileType = MeasureFileType.DYNAMIC;
        if (type != null) {
            if (type.toUpperCase().equals(MeasureFileType.TRIGGER.toString())) {
                fileType = MeasureFileType.TRIGGER;
            }
        }

        fileService.upload(id, dataLoggerId, fileType, file);
    }

    @GetMapping("/{dataLoggerId}/{fileName:.+}")
    public ResponseEntity<Resource> downloadId(
            @PathVariable Long dataLoggerId,
            @PathVariable String fileName,
            @NotNull HttpServletRequest request
    ) throws IOException {
        String id = getUserName(request);
        Resource resource = fileService.download(id, dataLoggerId, fileName);
        String contentType;
        contentType = request
                .getServletContext()
                .getMimeType(resource.getFile().getAbsolutePath());

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\""
                ).body(resource);
    }

    @NotNull
    private String getUserName(@NotNull HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = "";
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        }

        if (username.isEmpty()) throw new UsernameNotFoundException("Unauthorized");

        return username;
    }

    @GetMapping("/{dataLoggerId}")
    public Result<List<MeasureFileDto>> list(
            @PathVariable Long dataLoggerId,
            @RequestParam(required=false) String type,
            HttpServletRequest request
    ) {
        String id = getUserName(request);
        List<MeasureFileDto> list;

        if (type == null) {
            list = fileService.selectAll(id, dataLoggerId);
            return new Result<>(list.size(), list);
        }

        if (type.toUpperCase().equals(MeasureFileType.TRIGGER.toString())) {
            list = fileService.select(id, dataLoggerId, MeasureFileType.TRIGGER);
        } else if (type.toUpperCase().equals(MeasureFileType.DYNAMIC.toString())) {
            list = fileService.select(id, dataLoggerId, MeasureFileType.DYNAMIC);
        } else {
            list = fileService.selectAll(id, dataLoggerId);
        }
        return new Result<>(list.size(), list);
    }
}
