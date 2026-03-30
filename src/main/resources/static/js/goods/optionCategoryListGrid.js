/**
 * 옵션분류코드 AUI Grid 컨트롤러 (Vanilla JS)
 */
var OptionCategoryGrid = {
    id: "optionCategoryListGrid",
    grid: null,
    regGbSelect: {},
    typCdSelect: {},

    // DTO 기반의 Dropdown 리스트 변환 헬퍼 (HTML _optnCat... 변수 이용)
    buildDropdownList: function (obj) {
        var list = [];
        for (var key in obj) {
            list.push({ code: key, value: obj[key] });
        }
        return list;
    },

    init: function (regGbSelect, typCdSelect) {
        var _this = this;
        this.regGbSelect = regGbSelect || {};
        this.typCdSelect = typCdSelect || {};
        var regGbList = this.buildDropdownList(this.regGbSelect);
        var typCdList = this.buildDropdownList(this.typCdSelect);

        var columnLayout = [
            { dataField: "optnCatNo", headerText: "옵션분류번호", visible: false },
            {
                dataField: "optnCatRegGbCd", headerText: "사용처 *", width: 100,
                renderer: {
                    type: "DropDownListRenderer",
                    list: regGbList,
                    keyField: "code",   // 키 필드명 명시
                    valueField: "value" // 출력 필드명 명시
                },
                labelFunction: function (rowIndex, columnIndex, value, headerText, item) {
                    return _this.regGbSelect[value] || value;
                },
                editable: function (rowIndex, columnIndex, value, item) {
                    return item._$isNew === true && !window.isPartner;
                }
            },
            { dataField: "entrNo", headerText: "협력사번호", visible: false },
            {
                dataField: "entrNm", headerText: "협력사명 *", width: 150,
                renderer: {
                    type: "IconRenderer",
                    iconWidth: 16,
                    iconHeight: 16,
                    iconPosition: "aisleRight",
                    iconTableRef: { "default": "/images/btn_search.png" }, // 아이콘 경로 스터브
                    onClick: function (event) {
                        // 사용처가 협력사(02)이면서 새로 추가된 행(등록 전)일 때만 팝업 허용
                        if (event.item.optnCatRegGbCd === '02') {
                            if (event.item._$isNew) {
                                _this.openPartnerPopup(event.rowIndex);
                            } else {
                                alert("이미 등록된 데이터의 협력사는 변경할 수 없습니다.");
                            }
                        } else {
                            if (event.item._$isNew) {
                                alert("사용처가 '협력사'인 경우에만 업체를 선택할 수 있습니다.");
                            }
                        }
                    }
                },
                editable: false
            },
            {
                dataField: "optnCatTypCd", headerText: "분류유형 *", width: 100,
                renderer: {
                    type: "DropDownListRenderer",
                    list: typCdList,
                    keyField: "code",
                    valueField: "value"
                },
                labelFunction: function (rowIndex, columnIndex, value, headerText, item) {
                    return _this.typCdSelect[value] || value;
                },
                editable: function (rowIndex, columnIndex, value, item) {
                    return item._$isNew === true;
                }
            },
            { dataField: "optnCatNm", headerText: "옵션분류명 *", width: 150, style: "aui-text-left" },
            {
                dataField: "sortSeq", headerText: "정렬순서 *", width: 80,
                dataType: "numeric", formatString: "#0"
            },
            {
                dataField: "useYn", headerText: "사용여부 *", width: 80,
                renderer: { type: "CheckBoxEditRenderer", checkValue: "Y", unCheckValue: "N" }
            },
            { dataField: "langCd", visible: false },
            { dataField: "sysModId", headerText: "수정자", width: 100, editable: false },
            { dataField: "sysModDtm", headerText: "수정일시", width: 150, editable: false },
            { dataField: "sysRegId", headerText: "등록자", width: 100, editable: false },
            { dataField: "sysRegDtm", headerText: "등록일시", width: 150, editable: false }
        ];

        var gridProps = {
            editable: true,
            showStateColumn: true,
            showRowCheckColumn: true,
            selectionMode: "multipleCells",
            softRemoveRowMode: true
        };

        this.grid = AUIGrid.create(this.id, columnLayout, gridProps);
        this.bindEvents();
    },

    openPartnerPopup: function (rowIndex) {
        var _this = this;
        var popUrl = '/popup/enteranceMgmt.etEntrBaseListPopup.do';

        // 공통 팝업 객체가 정의되어 있을 시 호출, 아니면 브라우저 스터브 처리
        if (typeof window.popCommon === "function") {
            var pin = { argSelectType: '1', argEntrGbCd: '10', argTrdStatCd: '' };
            var POP_DATA = { url: popUrl, winName: 'etEntrListPopup', title: '협력사조회', width: 741, height: 700 };

            window.popupPartnerCallback = function (e) {
                var resultData = JSON.parse(e.data);
                if (resultData && resultData.length > 0) {
                    var data = resultData[0];
                    AUIGrid.setCellValue(_this.grid, rowIndex, "entrNo", data.entrNo);
                    AUIGrid.setCellValue(_this.grid, rowIndex, "entrNm", data.entrNm);
                }
            };
            window.popCommon(pin, POP_DATA, window.popupPartnerCallback);
        } else {
            // 바닐라 JS 스터브 (백엔드 통합 전)
            alert("[스터브] 협력사 검색 팝업 호출 대상: " + popUrl);
            AUIGrid.setCellValue(_this.grid, rowIndex, "entrNo", "E9999");
            AUIGrid.setCellValue(_this.grid, rowIndex, "entrNm", "샘플협력사(주)");
        }
    },

    bindEvents: function () {
        var _this = this;

        // 검색
        document.getElementById("btn_search").addEventListener("click", function (e) {
            e.preventDefault();
            var form = document.getElementById("optionCategoryListGridForm");
            var formData = new FormData(form);
            var data = {};
            formData.forEach(function (value, key) {
                data[key] = value;
            });

            // axios 사용 가정
            axios.get('/api/v1/goods/optionMgmt/getOptionCategoryList.do', { params: data })
                .then(function (res) {
                    if (res.data && res.data.success) {
                        AUIGrid.setGridData(_this.grid, res.data.data);
                    }
                })
                .catch(function (err) {
                    console.error("Search Fail:", err);
                });
        });

        // 초기화
        document.getElementById("btn_init").addEventListener("click", function (e) {
            e.preventDefault();
            document.getElementById("optionCategoryListGridForm").reset();
            AUIGrid.clearGridData(_this.grid);
        });

        // 행추가
        document.getElementById("btn_optn_ctg_add").addEventListener("click", function (e) {
            e.preventDefault();
            var newItem = {
                optnCatNo: "",
                optnCatRegGbCd: "",
                entrNm: "",
                optnCatTypCd: "",
                optnCatNm: "",
                sortSeq: 1,
                useYn: "Y"
            };
            AUIGrid.addRow(_this.grid, newItem, "last");
        });

        // 취소
        document.getElementById("btn_optn_ctg_cancel").addEventListener("click", function (e) {
            e.preventDefault();
            AUIGrid.restore(_this.grid, "all");
        });

        // 저장
        document.getElementById("btn_optn_ctg_save").addEventListener("click", function (e) {
            e.preventDefault();
            var addedRowItems = AUIGrid.getAddedRowItems(_this.grid);
            var editedRowItems = AUIGrid.getEditedRowItems(_this.grid);
            var removedRowItems = AUIGrid.getRemovedItems(_this.grid);

            if (addedRowItems.length === 0 && editedRowItems.length === 0 && removedRowItems.length === 0) {
                alert("변경된 데이터가 없습니다.");
                return;
            }

            var requestData = {
                create: addedRowItems,
                update: editedRowItems,
                delete: removedRowItems
            };

            axios.post('/api/v1/goods/optionMgmt/saveOptionCategoryList.do', requestData)
                .then(function (res) {
                    if (res.data && res.data.success) {
                        alert("성공적으로 저장되었습니다.");
                        document.getElementById("btn_search").click();
                    } else {
                        alert("저장 실패: " + (res.data.error ? res.data.error.message : "알 수 없는 오류"));
                    }
                })
                .catch(function (err) {
                    alert("통신 중 오류가 발생했습니다.");
                });
        });

        // 행 선택 변경 시 마스터-디테일 바인딩
        AUIGrid.bind(_this.grid, "selectionChange", function (e) {
            if (e.primeCell) {
                var item = e.primeCell.item;
                var optnCatNo = item.optnCatNo;

                // 하단 옵션 리스트 그리드가 초기화 되어 있다면 데이터 트리거
                if (window.OptionCodeGrid && OptionCodeGrid.grid) {
                    if (optnCatNo) {
                        OptionCodeGrid.loadData(optnCatNo);
                    } else {
                        // 새로 추가되어 DB에 아직 없는 행을 클릭한 경우 데이터 초기화
                        OptionCodeGrid.clearData();
                    }
                }
            }
        });
    }
};

// document.addEventListener("DOMContentLoaded", function() {
//     OptionCategoryGrid.init();
// });
