(function (w, $, d) {
    const DisplayCategoryTreePopup = {
        // 초기화
        init() {
            // jQuery 3.x compatibility patch for older zTree versions
            if (window.$ && !$.browser) {
                $.browser = { msie: false, version: 0 };
            }

            if (req.siteNo) {
                $("#siteNo").val(req.siteNo);
                $('#siteNo').attr("disabled", true);
            }

            this.bindButtonEvent();
            this.loadMalls($("#siteNo").val(), req.argDpmlNo);
        },

        loadMalls(siteNo, selectedDpmlNo) {
            if (!siteNo) return;
            $.ajax({
                type: "GET",
                url: "/popup/displayCategoryMgmtPopup.getMallList.do", // existing endpoint
                data: { siteNo: siteNo },
                success: function (res) {
                    const mallList = res.data || [];
                    let html = '<option value="">전시몰 선택</option>';
                    mallList.forEach(m => {
                        html += `<option value="${m.dpmlNo}" ${m.dpmlNo == selectedDpmlNo ? 'selected' : ''}>${m.dpmlNm}</option>`;
                    });
                    $("#dpmlNo").html(html);

                    // 만약 이미 선택된 몰이 있거나 목록이 하나뿐이면 트리 로딩
                    if ($("#dpmlNo").val()) {
                        DisplayCategoryTreePopup.treeLoading();
                    }
                }
            });
        },

        treeLoading() {
            const siteNo = $("#siteNo").val();
            const dpmlNo = $("#dpmlNo").val();

            if (!siteNo || !dpmlNo) {
                if (this.zTreeObj) this.zTreeObj.destroy();
                return;
            }

            const setNoneLeafCss = (treeId, treeNode) => {
                return !treeNode.isParent && treeNode.leafYn !== 'Y' ? { 'color': '#acacac', 'pointer-events': 'none' } : {};
            };
            // ... (rest of treeLoading logic)

            const setting = {
                view: {
                    showLine: true,
                    showIcon: true,
                    fontCss: setNoneLeafCss,
                    addDiyDom: function (treeId, treeNode) {
                        const aObj = $("#" + treeNode.tId + "_a");
                        const chkObj = $("#" + treeNode.tId + "_check");
                        const icoObj = $("#" + treeNode.tId + "_ico");

                        // 체크박스를 아이콘 바로 뒤(이름 앞)로 이동
                        if (chkObj.length > 0 && icoObj.length > 0) {
                            chkObj.insertAfter(icoObj);
                        }
                    }
                },
                check: {
                    enable: true,
                    chkboxType: { "Y": "s", "N": "s" }
                },
                data: {
                    key: {
                        name: "dispCtgNm"
                    },
                    simpleData: {
                        enable: true,
                        idKey: "dispCtgNo",
                        pIdKey: "uprDispCtgNo"
                    }
                },
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        // 클릭 시 자동 선택 토글 (창은 안닫힘)
                        DisplayCategoryTreePopup.zTreeObj.checkNode(treeNode, !treeNode.checked, true, true);
                    }
                }
            };

            // 적용 버튼 노출 처리 (체크박스 흐름으로 통일)
            $("#btn_popup_apply").show();

            const param = {
                siteNo: $("#siteNo").val(),
                dpmlNo: $("#dpmlNo").val(),
                useYn: $("#useYn").val()
            };

            $.ajax({
                type: "GET",
                url: "/popup/displayCategoryMgmtPopup.displayCategoryTreeList.do",
                data: param,
                // ... (rest of ajax)
                success: function (res) {
                    const zNodes = res.data || [];
                    if (zNodes.length == 0) {
                        alert("조회된 데이터가 없습니다.");
                    } else {
                        DisplayCategoryTreePopup.zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);

                        // 리프 노드만 체크박스 노출 처리 (항상 적용)
                        const nodes = DisplayCategoryTreePopup.zTreeObj.transformToArray(DisplayCategoryTreePopup.zTreeObj.getNodes());
                        nodes.forEach(n => {
                            if (n.leafYn !== 'Y') {
                                n.nocheck = true;
                            }
                        });
                        DisplayCategoryTreePopup.zTreeObj.refresh();

                        DisplayCategoryTreePopup.zTreeObj.expandAll(true);
                    }
                },
                error: function (xhr) {
                    console.error("Tree loading error:", xhr);
                }
            });
        },

        bindButtonEvent: function () {
            $('#btn_popup_close').on('click', () => w.close());
            $('#btn_popup_apply').on('click', () => this.apply());
            $('#siteNo').on('change', (e) => this.loadMalls($(e.target).val()));
            $('#dpmlNo').on('change', () => this.treeLoading());
            $('#useYn').on('change', () => this.treeLoading());
        },

        // 부모 창으로 데이터 전달
        sendData(data) {
            const dataStr = JSON.stringify(data);

            // 1. window.opener의 콜백 함수 호출 (공통 방식)
            if (window.opener && typeof window.opener.popupDisplayCategoryCallback === 'function') {
                window.opener.popupDisplayCategoryCallback(dataStr);
            }

            // 2. postMessage 방식 (백업)
            w.postMessage(dataStr, "*");

            w.close();
        },

        // 다건 적용
        apply() {
            const checkedNodes = this.zTreeObj.getCheckedNodes();
            let returnValues = [];

            checkedNodes.forEach(node => {
                if (node.leafYn === "Y") {
                    returnValues.push({
                        dispCtgNo: node.dispCtgNo,
                        dispCtgNm: node.dispCtgNm,
                        siteNo: node.siteNo,
                        siteNm: node.siteNm,
                        dpmlNo: node.dpmlNo,
                        dpmlNm: node.dpmlNm,
                        hierarchyNm: node.hierarchyText
                    });
                }
            });

            if (returnValues.length === 0) {
                alert("선택된 카테고리가 없습니다.");
                return;
            }

            this.sendData(returnValues);
        }
    };

    $(function () {
        DisplayCategoryTreePopup.init();
    });

})(window, jQuery, document);
