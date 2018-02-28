package com.rocoinfo.service.upload;

import com.rocoinfo.repository.upload.SequenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author zhangmin
 */
@Service
public class SequenceService {

    @Autowired
    private SequenceDao sequenceDao;

    /**
     * 增加sequence的值,然后返回增加后sequence值
     */
    public Integer getNextVal(SequenceTable seqTab) {
        sequenceDao.next(seqTab);
        return sequenceDao.getCurVal(seqTab);
    }


    public enum SequenceTable {
        UPLOAD, DICT_PLAN(4), ADV_TYPE(4), CENT_RULE(4), INSTORE_CODE(6), COMPANY_CODE(6);

        //返回的code sequence,格式化成固定的宽度
        private int fixWidth;

        SequenceTable() {
        }

        SequenceTable(int width) {
            this.fixWidth = width;
        }

        public int getFixWidth() {
            return this.fixWidth;
        }
    }
}

