package com.rocoinfo.rest.fileimport;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.service.task.TaskDistributeService;
import com.rocoinfo.utils.excel.UploadTaskExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 描述： 导入任务
 *
 * @author tony
 * @创建时间 2017-07-07 13:20
 */
@Controller
@RequestMapping(value = "/api/import")
public class FileImport extends BaseController {

    @Autowired
    private TaskDistributeService taskDistributeService;

    @RequestMapping(value = "/task")
    @ResponseBody
    public Object taskImport(@RequestParam("file") MultipartFile file,
                             @RequestParam("storeCode") String storeCode,
                             @RequestParam("dataSourceCode") String dataSourceCode,
                             HttpServletRequest request) {

        List<Map<String, String >> list = null;
        try {
            list = UploadTaskExcel.parserTaskExcel(file);
        } catch (Exception e) {
            return StatusDto.buildFailure(e.getMessage());
        }

        return taskDistributeService.taskImport(list, storeCode, dataSourceCode);
    }
}
