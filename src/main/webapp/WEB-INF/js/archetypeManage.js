$(document).ready(function () {
    var overrideChanged = false;
    var showFiles = [];

    $('#overrideChangedRadio').on('switch-change', function (e, data) {
        overrideChanged = data.value;
    });

    $('#searchDiv').on('click', '#searchButton', function () {
        var valueCondition = $('#searchConditionInput').val();
        window.location.href = "/clever-website/archetypeSearch?condition=" + valueCondition;
    });

    $("#displayFiles").on('click', '.cancelSingleFile', function () {
        $(this).parent().parent().remove();
        var canceledName = $(this).parent().prev().prev().prev().prev().text();
        for (var i = 0; i < showFiles.length; i++) {
            if (showFiles[i].name == canceledName) {
                showFiles.splice(i, 1);
                break;
            }
        }
    });

    $("#addFileDiv").on('click', '#uploadFlag', function () {
        $("#displayFiles .cancelSingleFile").attr("disabled", "disabled");
        $("#addFiles").attr("disabled", "disabled");
        var uploadCandidateFiles = [];
        for (var i = 0; i < showFiles.length; i++) {
            var fileState = $("#displayFiles td:contains(" + showFiles[i].name + ")").next().next().text();
            if (fileState == "") {
                $("#displayFiles .cancelSingleFile").removeAttr("disabled");
                $("#addFiles").removeAttr("disabled");
                var whetherValidateTip = noty({
                    text: "validating files...",
                    type: 'warning',
                    dismissQueue: true,
                    layout: 'bottomRight',
                    theme: 'defaultTheme'
                });
                return;
            }
            if (fileState == "NONE") {
                uploadCandidateFiles.push(showFiles[i]);
            }
            if (fileState == "CHANGED" && overrideChanged == true) {
                uploadCandidateFiles.push(showFiles[i]);
            }
        }
        if (uploadCandidateFiles.length > 0) {
            $("#uploadTip").html("<img src='image/progressbar.gif' width='80' height='30' style='vertical-align: middle;' />");
            var formData = new FormData();
            for (var i = 0; i < uploadCandidateFiles.length; i++) {
                formData.append(uploadCandidateFiles[i].name, uploadCandidateFiles[i]);
            }
            $.ajax({
                type: "post",
                url: "/clever-website/fileUpload",
                data: formData,
                dataType: "json",
                processData: false,  // tell jQuery not to process the data
                contentType: false,  // tell jQuery not to set contentType
                success: function (data, textStatus, jqXHR) {
                    $("#uploadTip").html("");
                    if (data.uploadResult == "fail") {
                        noty({
                            text: "upload files failes:" + data.uploadResultDescription,
                            type: 'error',
                            dismissQueue: true,
                            layout: 'bottomRight',
                            theme: 'defaultTheme'
                        });
                    } else if (data.uploadResult == "success") {
                        noty({
                            text: "upload files succeed",
                            type: 'success',
                            dismissQueue: true,
                            layout: 'bottomRight',
                            theme: 'defaultTheme'
                        });
                    }
                }
            });
            showFiles = [];
            $("#displayFiles").html("");
            $("#addFiles").removeAttr("disabled");
        } else if (uploadCandidateFiles.length == 0) {
            $("#displayFiles .cancelSingleFile").removeAttr("disabled");
            $("#addFiles").removeAttr("disabled");
            noty({
                text: "there are no files need to be upload",
                type: 'information',
                dismissQueue: true,
                layout: 'bottomRight',
                theme: 'defaultTheme'
            });
        }
    });

    $("#addFiles").on('change', 'input', function () {
        var thisTimeFiles = this.files;
        for (var i = 0; i < thisTimeFiles.length; i++) {
            var contained = false;
            for (var j = 0; j < showFiles.length; j++) {
                if (thisTimeFiles[i].name == showFiles[j].name) {
                    contained = true;
                    break;
                }
            }
            //when the file is not contained, push it into showFiles
            if (!contained) {
                showFiles.push(thisTimeFiles[i]);
                $("#displayFiles").append("<tr class='template-upload fade in' >" +
							     "<td>" + thisTimeFiles[i].name + "</td>" +
							     "<td>" + thisTimeFiles[i].size + "</td>" +
							     "<td></td>" +
							     "<td></td>" +
							     "<td>" +
								     "<button type='reset' class='btn btn-warning btn-xs cancelSingleFile' disabled='disabled'>" +
									    "<i class='glyphicon glyphicon-ban-circle'></i> <span>Cancel</span>" +
								     "</button>" +
								  "</td>" +
						        "</tr>");
                $("#displayFiles td:contains(" + thisTimeFiles[i].name + ")").next().next().next().html("<img src='image/loading.gif' width='30' height='30' style='vertical-align: middle;' />");
                var formData = new FormData();
                formData.append(thisTimeFiles[i].name, thisTimeFiles[i]);
                $.ajax({
                    type: "post",
                    url: "/clever-website/fileExist",
                    data: formData,
                    dataType: "json",
                    processData: false,  // tell jQuery not to process the data
                    contentType: false,  // tell jQuery not to set contentType
                }).done(function(data) {
                    $("#displayFiles td:contains(" + data.originalFileName + ")").next().next().text(data.existStatus);
                    $("#displayFiles td:contains(" + data.originalFileName + ")").next().next().next().text(data.existName);
                    $("#displayFiles td:contains(" + data.originalFileName + ")").next().next().next().next().find("button").removeAttr("disabled");
                });
            }
        }
        $("#inputFiles").remove();
        $("#addFiles").append("<input id='inputFiles'" + "type='file' name='files[]' multiple>");
    });
});
