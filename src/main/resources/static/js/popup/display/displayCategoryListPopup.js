var DisplayCategoryPopup = {
    zTreeObj: null,

    init: function () {
        if (window.$ && !$.browser) {
            $.browser = { msie: false, version: 0 };
        }

        this.bindButtonEvent();
        this.loadTree();
    },

    loadTree: function () {
        var _this = this;
        var siteNo = document.getElementById("siteNo").value;
        var dpmlNo = document.getElementById("dpmlNo").value;
        var useYn = document.getElementById("useYn").value;

        // AS-IS setNoneLeafCss implementation
        var setNoneLeafCss = function (treeId, treeNode) {
            // If it's a leaf node but leafYn is not 'Y', it means it's an empty intermediate node or similar
            return !treeNode.isParent && treeNode.leafYn !== 'Y' ? { 'color': '#d1d5db' } : {};
        };

        var treeSetting = {
            view: {
                showLine: true, // Show tree lines for depth distinction
                showIcon: true,
                fontCss: setNoneLeafCss,
                fontCss: setNoneLeafCss,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "dispCtgNo",
                    pIdKey: "uprDispCtgNo",
                    rootPId: null
                },
                key: {
                    name: "dispCtgNm"
                }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    if (_this.zTreeObj) {
                        _this.zTreeObj.expandNode(treeNode, !treeNode.open, false, true);
                    }
                },
                onNodeCreated: function (event, treeId, treeNode) {
                    // Direct DOM manipulation to hide checkbox for non-leaf categories
                    if (treeNode.leafYn !== 'Y') {
                        $("#" + treeNode.tId + "_check").hide();
                    }
                }
            }
        };

        // Multi-select or Single-select choice
        // argSelectType: '1' or 'S' (Single), '2' (Multi)
        var isMulti = (typeof argSelectType !== 'undefined' && argSelectType === "2");

        if (isMulti) {
            treeSetting.check = {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: { "Y": "", "N": "" } // Independent checkbox behavior like AS-IS
            };
        } else {
            treeSetting.callback.beforeClick = function (treeId, treeNode) {
                // In single select, usually we only allow selecting leaf nodes
                if (treeNode.leafYn !== 'Y') {
                    return false;
                }
                return true;
            };
        }

        var params = {
            siteNo: siteNo,
            dpmlNo: dpmlNo,
            useYn: useYn
        };

        axios.get("/api/v1/display/displayCategoryMgmt/getCategoryTreeList.do", {
            params: params
        }).then(function (res) {
            if (res.data && res.data.success) {
                var treeData = res.data.data.map(function (item) {
                    var isLeaf = (item.leafYn === 'Y');
                    return {
                        ...item,
                        isParent: !isLeaf,
                        nocheck: (isMulti && !isLeaf),
                        chkDisabled: (isMulti && !isLeaf)
                    };
                });

                var $treeTarget = $("#categoryTreePopup");
                if ($treeTarget.length > 0) {
                    // Destroy existing tree if any
                    $.fn.zTree.destroy("categoryTreePopup");

                    _this.zTreeObj = $.fn.zTree.init($treeTarget, treeSetting, treeData);

                    // Expand first node as in AS-IS
                    var nodes = _this.zTreeObj.getNodes();
                    if (nodes && nodes.length > 0) {
                        _this.zTreeObj.expandNode(nodes[0], true, false, false);
                    }
                }
            } else {
                $("#categoryTreePopup").html("<li style='padding:10px; color:#999;'>조회된 데이터가 없습니다.</li>");
            }
        }).catch(function (err) {
            console.error("Popup tree loading error:", err);
        });
    },

    apply: function () {
        var isMulti = (typeof argSelectType !== 'undefined' && argSelectType === "2");
        var selectedNodes = isMulti ? this.zTreeObj.getCheckedNodes(true) : this.zTreeObj.getSelectedNodes();
        var returnValues = [];

        selectedNodes.forEach(function (node) {
            // Final check to ensure only leaf nodes are passed
            if (node.leafYn === "Y") {
                var returnValue = {
                    dispCtgNo: node.dispCtgNo,
                    dispCtgNm: node.dispCtgNm,
                    siteNo: node.siteNo,
                    siteNm: node.siteNm,
                    dpmlNo: node.dpmlNo,
                    dpmlNm: node.dpmlNm,
                    hierarchyNm: node.hierarchyText,
                    lrgCtgNo: node.lrgCtgNo,
                    midCtgNo: node.midCtgNo,
                    smlCtgNo: node.smlCtgNo,
                    thnCtgNo: node.thnCtgNo
                };
                returnValues.push(returnValue);
            }
        });

        if (returnValues.length === 0) {
            alert("상위 화면에 적용할 리프 카테고리를 선택해주세요.");
            return;
        }

        if (window.opener && typeof window.opener.popupDisplayCategoryCallback === "function") {
            window.opener.popupDisplayCategoryCallback(returnValues);
            window.close();
        } else {
            console.error("Callback function 'popupDisplayCategoryCallback' not found in opener.");
            alert("부모 창의 콜백 함수를 찾을 수 없습니다.");
        }
    },

    bindButtonEvent: function () {
        var _this = this;
        $("#siteNo").on("change", function () {
            // Update Mall List when Site changes
            var siteNo = $(this).val();
            axios.get("/api/v1/display/displayCategoryMgmt/getMallList.do", { params: { siteNo: siteNo } })
                .then(function (res) {
                    var $dpmlNo = $("#dpmlNo");
                    $dpmlNo.empty();
                    $dpmlNo.append('<option value="">통합몰</option>');
                    if (res.data && res.data.data) {
                        res.data.data.forEach(function (mall) {
                            $dpmlNo.append('<option value="' + mall.dpmlNo + '">' + mall.dpmlNm + '</option>');
                        });
                    }
                    _this.loadTree();
                });
        });

        $("#dpmlNo, #useYn").on("change", function () {
            _this.loadTree();
        });

        $("#btn_popup_apply").on("click", function (e) {
            e.preventDefault();
            _this.apply();
        });

        $("#btn_popup_close").on("click", function (e) {
            e.preventDefault();
            window.close();
        });
    }
};

$(document).ready(function () {
    DisplayCategoryPopup.init();
});

