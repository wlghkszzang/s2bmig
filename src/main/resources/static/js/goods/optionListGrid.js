/**
 * 옵션코드 AUI Grid 컨트롤러 (Vanilla JS)
 */
var OptionCodeGrid = {
    id: "optionListGrid",
    grid: null,
    uploadDomain: "",

    init: function (uploadDomain) {
        var _this = this;
        this.uploadDomain = uploadDomain || "";

        var columnLayout = [
            { dataField: "optnCatNo", visible: false },
            {
                dataField: "optnNo", headerText: "옵션 번호 *", width: 100,
                editable: function (rowIndex, columnIndex, value, item) {
                    return item._$isNew === true; // 생성 시에만 수정 가능
                }
            },
            { dataField: "optnNm", headerText: "옵션 명 *", width: 150, style: "aui-text-left" },
            {
                dataField: "sortSeq", headerText: "정렬순서 *", width: 80,
                dataType: "numeric", formatString: "#0"
            },
            {
                dataField: "useYn", headerText: "사용여부 *", width: 80,
                renderer: { type: "CheckBoxEditRenderer", checkValue: "Y", unCheckValue: "N" }
            },
            {
                dataField: "imgPathNm", headerText: "이미지 썸네일", width: 100,
                renderer: { type: "IconRenderer", iconWidth: 20, iconHeight: 20 },
                labelFunction: function (rowIndex, columnIndex, value, headerText, item) {
                    // 전역 상수로 정의된 파일 서버 도메인 할당
                    return value ? (_this.uploadDomain) + value : "";
                }
            },
            {
                dataField: "rgbVal", headerText: "RGB코드", width: 100,
                labelFunction: function (rowIndex, columnIndex, value, headerText, item) {
                    if (value) {
                        return "<div style='background:" + value + "; border:1px solid #555; width:18px; height:18px; display:inline-block; vertical-align:middle;'></div> <span style='vertical-align:middle;'>" + value + "</span>";
                    }
                    return "";
                }
            },
            { dataField: "imgFileNm", visible: false },
            { dataField: "langCd", visible: false },
            { dataField: "sysModId", headerText: "수정자", width: 80, editable: false, style: "disable-column" },
            { dataField: "sysModDtm", headerText: "수정일시", width: 150, editable: false, style: "disable-column" },
            { dataField: "sysRegId", headerText: "등록자", width: 80, editable: false, style: "disable-column" },
            { dataField: "sysRegDtm", headerText: "등록일시", width: 150, editable: false, style: "disable-column" }
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

    clearData: function () {
        if (this.grid) {
            AUIGrid.clearGridData(this.grid);
        }
    },

    loadData: function (optnCatNo) {
        var _this = this;
        var data = { optnCatNo: optnCatNo };

        axios.get('/api/v1/goods/optionMgmt/getOptionList.do', { params: data })
            .then(function (res) {
                if (res.data && res.data.success) {
                    AUIGrid.setGridData(_this.grid, res.data.data);
                }
            })
            .catch(function (err) {
                console.error("Option Load Fail:", err);
                alert("옵션 목록을 불러오는 중 오류가 발생했습니다.");
            });
    },

    bindEvents: function () {
        var _this = this;

        document.getElementById("btn_optn_add").addEventListener("click", function (e) {
            e.preventDefault();
            // OptionCategoryGrid 모듈 연동 확인
            if (!window.OptionCategoryGrid || !OptionCategoryGrid.grid) return;

            var selectedItems = AUIGrid.getSelectedItems(OptionCategoryGrid.grid);
            if (selectedItems.length === 0) {
                alert("옵션분류 목록을 먼저 선택하세요.");
                return;
            }
            var optnCatNo = selectedItems[0].item.optnCatNo;

            var newItem = {
                optnCatNo: optnCatNo,
                optnNo: "",
                optnNm: "",
                sortSeq: 1,
                useYn: "Y",
                imgPathNm: "",
                rgbVal: ""
            };
            AUIGrid.addRow(_this.grid, newItem, "last");
        });

        document.getElementById("btn_optn_img_add").addEventListener("click", function (e) {
            e.preventDefault();
            alert("이미지 첨부 팝업/로직 호출");
        });

        document.getElementById("btn_optn_cancel").addEventListener("click", function (e) {
            e.preventDefault();
            AUIGrid.restore(_this.grid, "all");
        });

        document.getElementById("btn_optn_save").addEventListener("click", function (e) {
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

            axios.post('/api/v1/goods/optionMgmt/saveOptionList.do', requestData)
                .then(function (res) {
                    if (res.data && res.data.success) {
                        alert("옵션 코드가 성공적으로 저장되었습니다.");
                    } else {
                        alert("저장 실패: " + (res.data.error ? res.data.error.message : "알 수 없는 오류"));
                    }
                })
                .catch(function (err) {
                    alert("시스템 오류가 발생했습니다.");
                });
        });
    }
};

// document.addEventListener("DOMContentLoaded", function() {
//     OptionCodeGrid.init();
// });
