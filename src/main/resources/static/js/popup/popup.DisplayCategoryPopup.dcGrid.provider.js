var col = {
    disp_ctg_no    	 : '전시카테고리 번호',
    site_no    		 : '사이트 번호',
    site_nm    		 : '사이트명',
    dpml_no    	 : '전시몰 번호',
    dpml_nm    	 : '전시몰명',
    lrg_ctg_no    	 : '대분류 번호',
    lrg_ctg_nm 		 : '대분류명',
    mid_ctg_no       : '중분류 번호',
    mid_ctg_nm 		 : '중분류명',
    sml_ctg_no		 : '소분류 번호',
    sml_ctg_nm		 : '소분류명',
    thn_ctg_no 	     : '세분류 번호',
    thn_ctg_nm       : '세분류명',
    use_yn 	         : '사용여부'
};

$.namespace("dcGrid.settings");
dcGrid.settings = {
    fields : [ {
        fieldName : "dispCtgNo"
    }, {
        fieldName : "dispCtgNm"
    }, {
        fieldName : "siteNo"
    }, {
        fieldName : "siteNm"
    }, {
        fieldName : "dpmlNo"
    }, {
        fieldName : "dpmlNm"
    }, {
        fieldName : "lrgCtgNo"
    }, {
        fieldName : "lrgCtgNm"
    }, {
        fieldName : "midCtgNo"
    }, {
        fieldName : "midCtgNm"
    }, {
        fieldName : "smlCtgNo"
    }, {
        fieldName : "smlCtgNm"
    }, {
        fieldName : "thnCtgNo"
    }, {
        fieldName : "thnCtgNm"
    }, {
        fieldName : "useYn"
    } ],
    columns : [ {
        name : "dispCtgNo",
        fieldName : "dispCtgNo",
        visible : false
    }, {
        name : "dispCtgNm",
        fieldName : "dispCtgNm",
        visible : false
    }, {
        name : "siteNo",
        fieldName : "siteNo",
        visible : false
    }, {
        name : "siteNm",
        fieldName : "siteNm",
        header : {
            text : col.site_nm
        },
        width : 150,
        styleName : "left-column"
    }, {
        name : "dpmlNo",
        fieldName : "dpmlNo",
        visible : false
    }, {
        name : "dpmlNm",
        fieldName : "dpmlNm",
        header : {
            text : col.dpml_nm
        },
        width : 150,
        styleName : "left-column"
    }, {
        name : "lrgCtgNo",
        fieldName : "lrgCtgNo",
        header : {
            text : col.lrg_ctg_no
        },
        width : 100
    }, {
        name : "lrgCtgNm",
        fieldName : "lrgCtgNm",
        header : {
            text : col.lrg_ctg_nm
        },
        width : 150,
        styleName : "left-column"
    }, {
        name : "midCtgNo",
        fieldName : "midCtgNo",
        header : {
            text : col.mid_ctg_no
        },
        width : 100
    }, {
        name : "midCtgNm",
        fieldName : "midCtgNm",
        header : {
            text : col.mid_ctg_nm
        },
        width : 150,
        styleName : "left-column"
    }, {
        name : "smlCtgNo",
        fieldName : "smlCtgNo",
        header : {
            text : col.sml_ctg_no
        },
        width : 100
    }, {
        name : "smlCtgNm",
        fieldName : "smlCtgNm",
        header : {
            text : col.sml_ctg_nm
        },
        width : 150,
        styleName : "left-column"
    }, {
        name : "thnCtgNo",
        fieldName : "thnCtgNo",
        header : {
            text : col.thn_ctg_no
        },
        width : 100
    }, {
        name : "thnCtgNm",
        fieldName : "thnCtgNm",
        header : {
            text : col.thn_ctg_nm
        },
        width : 150,
        styleName : "left-column"
    }, {
        name : "useYn",
        fieldName : "useYn",
        header : {
            text : col.use_yn
        },
        width : 80
    } ],
    props : {
        paging : true,
        width : "100%",
        autoFitHeight : true,
        popup : true,
        sumRowVisible : false,
        checkbox : true,
        crud : false,
        form : "dcGridForm",
        action : _baseUrl + "popup/displayCategoryMgmtPopup.getDisplayCategoryList.do"
    }
};
