/**
 * Created by Maurya on 22/06/2015.
 */
jq = jQuery;

//////////////
// Initialize DOM elements and their respective handlers
//////////////
jq(document).ready(function(){

    // setting defaults for the editable
    $.fn.editable.defaults.mode = 'inline';
    $.fn.editable.defaults.showbuttons = true;
    $.fn.editable.defaults.type = 'text';

    var OpenMRSInstance=window.location.href;
    jq("#navigationLogout").attr("href",OpenMRSInstance.split("/patientportaltoolkit")[0]+"/logout");
    jq(".imagePlaceHolders").attr("src",OpenMRSInstance.split("/patientportaltoolkit")[0]+"/images/openmrs_logo_white.gif");
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
    
    var markScheduledDatePicker= jq( "#markScheduledDate" ).datepicker({
        onRender: function(date) {
            return date.valueOf() < now.valueOf() ? 'disabled' : '';
        },
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        markScheduledDatePicker.hide();
    }).data('datepicker');
    var userprofileDOBDatePicker= jq( "#userprofileDOB" ).datepicker({
        onRender: function(date) {
            return date.valueOf() > now.valueOf() ? 'disabled' : '';
        },
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        userprofileDOBDatePicker.hide();
    }).data('datepicker');

    jq(".reformatText").each(function() {
        var str=$(this).text().toString();
        var newStr=str[0].toUpperCase()+str.slice(1).toLowerCase();
        $(this).text(newStr);
    });
    jq(function() {
        jq('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            localStorage.setItem('lastTab', jq(this).attr('href'));
        });
        var lastTab = localStorage.getItem('lastTab');
        if (lastTab) {
            jq('[href="' + lastTab + '"]').tab('show');
        }
    });

    jq(".editGenHistButton").click(
        function () {
            jq("#genHistEncounterHolder").val(this.id);
        });
   var genHistdatePicker= jq( ".gen-history-date" ).datepicker({
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
       genHistdatePicker.hide();
   }).data('datepicker');

    jq(".cancelModal").click(
        function () {
            location.reload();
        });
    jq('.editSurgeryButton').click(
        function () {
            var encounterID = this.id;
            jq("#surgeryEncounterHolder").val(encounterID);
            var surgeryTypeList = [];
            jq('.' + encounterID + 'surgeryType').each(function () {
                surgeryTypeList.push((jq(this).attr('id').split('surgeryType')[1]));
            });
            jq('.surgeryTypesInModal').each(function () {
                if (jq.inArray((jq(this).val()).split('split')[1], surgeryTypeList) > -1) {
                    jq(this).prop('checked', true);
                }
            });

            if (jq('#' + encounterID + 'surgeryComplications').text() != null || jq('#' + encounterID + 'surgeryComplications').text() != '') {
                jq('#majorComplicationsBoolSelect').val('1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA');
                jq('#majorComplicationsTypeAnswer').val(jq('#' + encounterID + 'surgeryComplications').text());
            } else {
                jq('#majorComplicationsBoolSelect').val('1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA');
            }
            jq("#surgeryDate").val($.datepicker.formatDate('mm/dd/yy', new Date(jq('#' + encounterID + 'surgeryDate').text())));
            // console.log($('#'+encounterID+'surgeryPCPName').text());
            jq("#surgeonPcpName").val(jq('#' + encounterID + 'surgeryPCPName').text());
            jq("#surgeonPcpEmail").val(jq('#' + encounterID + 'surgeryPCPEmail').text());
            jq("#surgeonPcpPhone").val(jq('#' + encounterID + 'surgeryPCPPhone').text());

            jq("#surgeryInstitutionName").val(jq('#' + encounterID + 'surgeryinstituteName').text());
            jq("#surgeryInstitutionCity").val(jq('#' + encounterID + 'surgeryCity').text());
            jq("#surgeryInstitutionState").val(jq('#' + encounterID + 'surgeryState').text());
        });


    $('.editChemotherapyButton').click(
        function () {
            var encounterID=this.id;
            $("#chemotherapyEncounterHolder").val(encounterID);
            var chemotherapyMedList=[];
            $('.'+encounterID+'chemotherapymed').each(function() {
                // console.log(($( this ).attr('id').split('surgeryType')[1]));
                chemotherapyMedList.push(($( this ).attr('id').split('chemotherapymed')[1]));
            });
            $('.chemotherapyMedTypesInModal').each(function() {
                if ( $.inArray(($( this ).val()).split('split')[1], chemotherapyMedList)>-1) {
                    $(this).prop('checked', true);
                }
            });

            if($('#'+encounterID+'centralLine').text()=="Yes"){
                $('#centralLineBoolSelect').val('1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA');
            }
            else{
                $('#centralLineBoolSelect').val('1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA');
            }
            $('#chemoStartDate').val($.datepicker.formatDate('mm/dd/yy', new Date($('#'+encounterID+'chemotherapyStartDate').text())));
            $('#chemoEndDate').val($.datepicker.formatDate('mm/dd/yy', new Date($('#'+encounterID+'chemotherapyEndDate').text())));
            $("#oncologistPcpName").val($('#'+encounterID+'chemotherapyPCPName').text());
            $("#oncologistPcpEmail").val($('#'+encounterID+'chemotherapyPCPEmail').text());
            $("#oncologistPcpPhone").val($('#'+encounterID+'chemotherapyPCPPhone').text());

            $("#chemotherapyInstitutionName").val($('#'+encounterID+'chemotherapyinstituteName').text());
            $("#chemotherapyInstitutionCity").val($('#'+encounterID+'chemotherapyCity').text());
            $("#chemotherapyInstitutionState").val($('#'+encounterID+'chemotherapyState').text());
        });


    var radiationStartdatePicker= jq( "#radiationStartDate" ).datepicker({
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        radiationStartdatePicker.hide();
    }).data('datepicker');
    var radiationEnddatePicker= jq( "#radiationEndDate" ).datepicker({
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        radiationEnddatePicker.hide();
    }).data('datepicker');

    $('.editRadiationButton').click(
        function () {
            var encounterID=this.id;
            $("#radiationEncounterHolder").val(encounterID);
            var radiationTypesList=[];
            $('.'+encounterID+'radiationType').each(function() {
                radiationTypesList.push(($( this ).attr('id').split('radiationType')[1]));
            });
            $('.radiationTypesInModal').each(function() {
                if ( $.inArray(($( this ).val()).split('split')[1], radiationTypesList)>-1) {
                    $(this).prop('checked', true);
                }
            });
            $('#radiationStartDate').val($.datepicker.formatDate('mm/dd/yy', new Date($('#'+encounterID+'radStartDate').text())));
            if($('#'+encounterID+'radEndDate').text())
            $('#radiationEndDate').val($.datepicker.formatDate('mm/dd/yy', new Date($('#'+encounterID+'radEndDate').text())));
            $("#radiologistPcpName").val($('#'+encounterID+'radPCPName').text());
            $("#radiologistPcpEmail").val($('#'+encounterID+'radPCPEmail').text());
            $("#radiologistPcpPhone").val($('#'+encounterID+'radPCPPhone').text());

            $("#radiologistInstitutionName").val($('#'+encounterID+'radinstituteName').text());
            $("#radiologistInstitutionCity").val($('#'+encounterID+'radCity').text());
            $("#radiologistInstitutionState").val($('#'+encounterID+'radState').text());
        });

    jq(".removeRelationCloseButton").click(
        function () {
            jq("#remove-relationId").val(this.id.split("removeRelation")[1]);
        });
    jq("#relation-delete-btn").click(
        function () {
            jq.get("removeRelationship/removeRelationship.action", {relationshipId:  jq("#remove-relationId").val()}, function(){
                location.reload();
            });
        });

    //------------- Add Relation Button save JS ---------

    jq("#addrelationshipbutton").click(
        function () {
            var checkboxValuesList=[];
            jq(".addRelationShareCheckbox:checkbox:checked").each(function () {
                checkboxValuesList.push($(this).val());
                //alert(checkboxValues);
                //checkboxValues=checkboxValues+$(this).val()+",";
            });
            var checkboxValues=checkboxValuesList.toString();
            $.ajax({
                type : "GET",
                url : "connections/addRelationship/addRelationshipfromForm.action",
                data : {
                    given:  jq("#givenpersonName").val(),
                    family: jq("#familypersonName").val(),
                    gender:jq("#genderSelect").val(),
                    personEmail:jq("#personEmail").val(),
                    personRelationType:jq("#addRelationshipSelect").val(),
                    securityLayerType: checkboxValues
                },
                success : function() {
                    setTimeout(function(){
                        location.reload();
                    }, 2000);
                },
                error : function(e) {
                    alert('Error: ' + e);
                },
            });
        });
    //------------- Add Relation Button save JS Ends ---------

//------------- Edit Relation Button JS ---------
    $('.editRelationButton').click(
        function () {
            var relationshipID=this.id.split('relationedit')[1];
            //console.log(relationshipID);
            $("#editRelationshipIdHolder").val(relationshipID);
            //console.log($('#'+relationshipID+'relationPerson').text());

            // console.log($('#'+encounterID+'surgeryPCPName').text());
            $("#editPersonName").text($('#'+relationshipID+'relationPerson').text());
            $("#editRelationProfileBadge").text($('#'+relationshipID+'relationPerson').text().match(/\b(\w)/g).join(''));
            $("#editRelationshipSelect").val($('#'+relationshipID+'relationType').text());
            $("#editRelationSecurityLevels").val($('#'+relationshipID+'relationShareID').val());
            if($('#checkPersonInRelation'+relationshipID).val()== "0"){
                $("#editRelationshipSelect").attr('disabled',true);
            }

            $.ajax({
                type : "POST",
                url : OpenMRSInstance.split("/patientportaltoolkit")[0]+"/ws/patientportaltoolkit/hasaccess",
                data : {
                    relationshipId: relationshipID,
                    shareType: "6776d050-e2fe-47cc-8af4-de3fdeb1b76d"
        },
                success : function setChecked(response) {
                    if (response) {
                        $("#editShareType" + "6776d050-e2fe-47cc-8af4-de3fdeb1b76d").prop('checked', true);
                    }
                    else {
                        $("#editShareType" + "6776d050-e2fe-47cc-8af4-de3fdeb1b76d").prop('checked', false);
                    }
                },
                error : function(e) {
                    alert('Error: ' + e);
                },
            });
            $.ajax({
                type : "POST",
                url : OpenMRSInstance.split("/patientportaltoolkit")[0]+"/ws/patientportaltoolkit/hasaccess",
                data : {
                    relationshipId: relationshipID,
                    shareType: "18e440a6-518b-4dbd-8057-dd0f88ee6d15"
                },
                success : function setChecked(response) {
                    if (response) {
                        $("#editShareType" + "18e440a6-518b-4dbd-8057-dd0f88ee6d15").prop('checked', true);
                    }
                    else {
                        $("#editShareType" + "18e440a6-518b-4dbd-8057-dd0f88ee6d15").prop('checked', false);
                    }
                },
                error : function(e) {
                    alert('Error: ' + e);
                },
            });
            $.ajax({
                type : "POST",
                url : OpenMRSInstance.split("/patientportaltoolkit")[0]+"/ws/patientportaltoolkit/hasaccess",
                data : {
                    relationshipId: relationshipID,
                    shareType: "c21b5749-5972-425b-a8dc-15dc8f899a96"
                },
                success : function setChecked(response) {
                    if (response) {
                        $("#editShareType" + "c21b5749-5972-425b-a8dc-15dc8f899a96").prop('checked', true);
                    }
                    else {
                        $("#editShareType" + "c21b5749-5972-425b-a8dc-15dc8f899a96").prop('checked', false);
                    }
                },
                error : function(e) {
                    alert('Error: ' + e);
                },
            });
        });

    //------------------- Edit Relation Button JS Ends ----------------------


//------------- Edit Relation Button save JS ---------
    $('#editRelationshipSaveButton').click(
        function () {
            var checkboxValuesList=[];
            jq(".editRelationShareCheckbox:checkbox:checked").each(function () {
                checkboxValuesList.push($(this).val());
                //checkboxValues=checkboxValues+$(this).val()+",";
           });
            var checkboxValues=checkboxValuesList.toString();
            $.ajax({
                type : "POST",
                url : "connections/connections/saveRelationshipfromEdit.action",
                data : {
                    relationshipId: jq("#editRelationshipIdHolder").val(),
                    personRelationType: jq("#editRelationshipSelect").val(),
                    personRelationSecurityLayer: checkboxValues
                },
                success : function() {
                    setTimeout(function(){
                        location.reload();
                    }, 2000);
                },
                error : function(e) {
                    alert('Error: ' + e);
                },
            });
            //$("#specialty").val( chkbxValues.join(",") );
            //jq.("connections/saveRelationshipfromEdit.action", {relationshipId:  jq("#editRelationshipIdHolder").val(),personRelationType:  jq("#editRelationshipSelect").val(),personRelationSecurityLayer:checkboxValues}, function(){
           // setTimeout(function(){
            //    location.reload();
           // }, 10000);
        });

    //------------------- Edit Relation Button save JS Ends -----

    //------------- accept connection request Button JS ----------------
    $('.acceptConnectionRequest').click(
        function () {
            var relationId = this.id.split("acceptConnectionRequest")[0];
            jq.get("connections/connections/acceptConnectionRequest.action", {relationshipId: relationId}, function(){
            });
            setTimeout(function(){
                location.reload();
            }, 2000);
        });
    //------------------- accept connection request Button save JS Ends -----

    //------------- ignore connection request Button JS ----------------
    $('.ignoreConnectionRequest').click(
        function () {
            var relationId = this.id.split("ignoreConnectionRequest")[0];
            jq.get("connections/ignoreConnectionRequest.action", {relationshipId: relationId}, function(){
            });
            setTimeout(function(){
                location.reload();
            }, 2000);
        });
    //------------------- ignore connection request Button save JS Ends -----
    //------------------- Messages Page JS ----------------------

    $('#newMessageComposeDiv').hide();
    $('#showDetailedList').hide();
    $('.detailedMessageList').hide();
    $('#composeMessageButton').click(
        function () {
            $('#newMessageComposeDiv').show();
            $('#showDetailedList').hide();
            $('.detailedMessageList').hide();
        });
    $('.messagelistLink').click(
        function () {
            $(".messagelistLink").css("background", "#FFFFFF");
            $("#"+this.id).css("background", "#F8F8F8");
            $('#newMessageComposeDiv').hide();
            $('#showDetailedList').show();
           // console.log(this.id);
            $('.detailedMessageList').hide();
            $('#mediaList'+this.id).show();
        });
    //------------------- Messages Page JS Ends ----------------------
        $('.profileBadge').profileBadge();
    $('.profileBadgeJournals').profileBadge({
        border: {
            width: 0
        },
        margin: 0,
        size: 30
    });
    $('.profileBadgeHeader').profileBadge({
        border: {
            width: 0
        },
        margin: 0,
        size: 90
    });

    //------------------- Reply message JS ----------------------
    $('.sendReplyMessageButton').click(
        function () {
           var messageid=this.id.split("sendReplyMessageButton")[1];
            jq.get("composeMessage/sendReplyMessage.action", {
                personUuid: jq("#replypersonId"+messageid).val(),
                subject: jq("#sendingReplyMessageSubject"+messageid).val(),
                message: jq("#sendingReplyMessageText"+messageid).val(),
                parentId: jq("#replythreadparentid"+messageid).val()
            }, function () {
            });
            setTimeout(
                function () {
                    location.reload();
                }, 2000);
        });
    //------------------- Reply message JS Ends ----------------------

    //------------------- compose message JS ----------------------
   var listOfRelationsData=[];
    $.when(  $.get(OpenMRSInstance.split("/patientportaltoolkit")[0]+"/ws/patientportaltoolkit/getallrelations",
        function(data, status) {

            $.each(data, function(k, v) {
                //display the key
                var relationitem = {id:data[k]["relatedPerson"]["id"], value:data[k]["relatedPerson"]["GivenName"]+data[k]["relatedPerson"]["FamilyName"]};
                listOfRelationsData.push(relationitem);
                //console.log(relationitem);
            });
        }) ).then(function() {
        //console.log(listOfRelationsData);
        $( "#sendingto" ).autocomplete({
            source: listOfRelationsData,
            minLength: 3,
            select: function(event, ui) {
                //alert(ui.item.toString());
                event.preventDefault();

                $("#sendingto").val(ui.item.value);
                $("#sendingPersonUUID").val(ui.item.id);
            },
            focus: function(event, ui) {
                event.preventDefault();
                $("#sendingto").val(ui.item.value);
            }
        });
    });

    $('#sendNewMessageButton').click(
        function () {
                jq.get("composeMessage/sendNewMessage.action", {
                    personUuid: jq("#sendingPersonUUID").val(),
                    subject: jq("#sendingNewMessageSubject").val(),
                    message: jq("#sendingNewMessageText").val(),
                }, function () {
                });
                setTimeout(
                    function () {
                        location.reload();
                    }, 3000);
        });

    //------------------- compose message JS ENDS ----------------------

    //------------------- Follow up care JS ----------------------

    //JS for the Button Events

    
    $('.markScheduledReminder').click(
        function () {
            var reminderID=this.id.split("markScheduledReminder")[1];
            $('#markScheduledIdHolder').val(reminderID);
        });
    $('#saveMarkScheduledButton').click(
        function () {
            jq.get("appointments/markScheduled.action", {reminderId: jq("#markScheduledIdHolder").val(), markScheduledDate: jq("#markScheduledDate").val()}, function(){
            });
            location.reload();
        });
    //JS for the Button Events END

    //------------------- Follow up care JS ENDS ----------------------

    // make all items having class 'edit' editable
    $('.edit').editable();

    $('[data-toggle="tooltip"]').tooltip();


    $('.mycancerbuddies').click(
        function () {
            $('#mycancerbuddiesSave').removeClass('disabled');
            $('#mycancerbuddiesSave').prop('disabled', false);
        });
    //------------------- Function to log events ----------------------

});
function logEvent (event,data) {
    $.ajax({
        type : "POST",
        url : window.location.href.split("/patientportaltoolkit")[0]+"/ws/patientportaltoolkit/logEvent",
        data : {
            event: event,
            data: data
        },
        success : function (response) {
            console.log(response);
        },
        error : function(e) {
            console.log('Error: ' + e);
        }
    });
}