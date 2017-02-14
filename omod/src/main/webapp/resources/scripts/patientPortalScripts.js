/**
 * Created by Maurya on 22/06/2015.
 */
jq = jQuery;

jq(document).ready(function(){

    function editEvent(event) {
        $('#event-modal input[name="event-index"]').val(event ? event.id : '');
        $('#event-modal input[name="event-name"]').val(event ? event.name : '');
        $('#event-modal input[name="event-location"]').val(event ? event.location : '');
        $('#event-modal input[name="event-start-date"]').datepicker('update', event ? event.startDate : '');
        $('#event-modal input[name="event-end-date"]').datepicker('update', event ? event.endDate : '');
        $('#event-modal').modal();
    }

    function deleteEvent(event) {
        var dataSource = $('#calendar').data('calendar').getDataSource();

        for(var i in dataSource) {
            if(dataSource[i].id == event.id) {
                dataSource.splice(i, 1);
                break;
            }
        }

        $('#calendar').data('calendar').setDataSource(dataSource);
    }

    function saveEvent() {
        var event = {
            id: $('#event-modal input[name="event-index"]').val(),
            name: $('#event-modal input[name="event-name"]').val(),
            location: $('#event-modal input[name="event-location"]').val(),
            startDate: $('#event-modal input[name="event-start-date"]').datepicker('getDate'),
            endDate: $('#event-modal input[name="event-end-date"]').datepicker('getDate')
        }

        var dataSource = $('#calendar').data('calendar').getDataSource();

        if(event.id) {
            for(var i in dataSource) {
                if(dataSource[i].id == event.id) {
                    dataSource[i].name = event.name;
                    dataSource[i].location = event.location;
                    dataSource[i].startDate = event.startDate;
                    dataSource[i].endDate = event.endDate;
                }
            }
        }
        else
        {
            var newId = 0;
            for(var i in dataSource) {
                if(dataSource[i].id > newId) {
                    newId = dataSource[i].id;
                }
            }

            newId++;
            event.id = newId;

            dataSource.push(event);
        }

        $('#calendar').data('calendar').setDataSource(dataSource);
        $('#event-modal').modal('hide');
    }


    var currentYear = new Date().getFullYear();
    var currentMonth = new Date().getMonth();
    var currentDay = new Date().getDate();
    var circleDateTime = new Date(currentYear, currentMonth, currentDay).getTime();

    function foo2 () {
        //hardcoded data to be modified
        $.get("http://localhost:8081/openmrs2/ws/patientportaltoolkit/getpreventivecareforpatient/d99d2110-7cad-4282-9e00-ede06b92c965", function (pcgeData) {
            $('#preventativeCareCalendar').data('calendar').setDataSource(pcgeData);
        });
    }
    var preventivecalendar= $('#preventativeCareCalendar').calendar({
        customDayRenderer: function(element, date) {
            if(date.getTime() == circleDateTime) {
                $(element).css('background-color', 'red');
                $(element).css('color', 'white');
                $(element).css('border-radius', '15px');
            }
        },
        enableContextMenu: true,
        contextMenuItems:[
            {
                text: 'Update',
                click: editEvent
            },
            {
                text: 'Delete',
                click: deleteEvent
            }
        ],
        selectRange: function(e) {
            editEvent({ startDate: e.startDate, endDate: e.endDate });
        },
        mouseOnDay: function(e) {
            if(e.events.length > 0) {
                var content = '';

                for(var i in e.events) {
                    content += '<div class="event-tooltip-content">'
                        + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                        + '<div class="event-location">' + e.events[i].location + '</div>'
                        + '</div>';
                }

                $(e.element).popover({
                    trigger: 'manual',
                    container: 'body',
                    html:true,
                    content: content
                });

                $(e.element).popover('show');
            }
        },
        mouseOutDay: function(e) {
            if(e.events.length > 0) {
                $(e.element).popover('hide');
            }
        }

    });

//js for preventative care events


   /* function foo (){
        console.info("foo ftw");
        $('#calendar').data('calendar').setDataSource([
            {
                id: 0,
                name: 'Google I/O',
                location: 'San Francisco, CA',
                startDate:new Date("02/16/2016"),
                endDate: new Date("02/16/2016")
            },
            {
                id: 1,
                name: 'Microsoft Convergence',
                location: 'New Orleans, LA',
                startDate: new Date("02/16/2016"),
                endDate: new Date("02/16/2016")
            },
            {
                id: 2,
                name: 'Microsoft Build Developer Conference',
                location: 'San Francisco, CA',
                startDate: new Date(currentYear, 3, 29),
                endDate: new Date(currentYear, 4, 1)
            },
            {
                id: 3,
                name: 'Apple Special Event',
                location: 'San Francisco, CA',
                startDate: new Date(currentYear, 8, 1),
                endDate: new Date(currentYear, 8, 1)
            },
            {
                id: 4,
                name: 'Apple Keynote',
                location: 'San Francisco, CA',
                startDate: new Date(currentYear, 8, 9),
                endDate: new Date(currentYear, 8, 9)
            },
            {
                id: 5,
                name: 'Chrome Developer Summit',
                location: 'Mountain View, CA',
                startDate: new Date(currentYear, 10, 17),
                endDate: new Date(currentYear, 10, 18)
            },
            {
                id: 6,
                name: 'F8 2015',
                location: 'San Francisco, CA',
                startDate: new Date(currentYear, 2, 25),
                endDate: new Date(currentYear, 2, 26)
            },
            {
                id: 7,
                name: 'Yahoo Mobile Developer Conference',
                location: 'New York',
                startDate: new Date(currentYear, 7, 25),
                endDate: new Date(currentYear, 7, 26)
            },
            {
                id: 8,
                name: 'Android Developer Conference',
                location: 'Santa Clara, CA',
                startDate: new Date(currentYear, 11, 1),
                endDate: new Date(currentYear, 11, 4)
            },
            {
                id: 9,
                name: 'LA Tech Summit',
                location: 'Los Angeles, CA',
                startDate: new Date(currentYear, 10, 17),
                endDate: new Date(currentYear, 10, 17)
            }
        ]);
        //alert(new Date(currentYear, 10, 17));
    }*/


    setTimeout(foo2, 1000);
   // calendar.setYear(new Date().getFullYear());


    // setting defaults for the editable
    $.fn.editable.defaults.mode = 'inline';
    $.fn.editable.defaults.showbuttons = true;
    $.fn.editable.defaults.type = 'text';

    var OpenMRSInstance=window.location.href;
    jq("#navigationLogout").attr("href",OpenMRSInstance.split("/patientportaltoolkit")[0]+"/logout");
    jq(".imagePlaceHolders").attr("src",OpenMRSInstance.split("/patientportaltoolkit")[0]+"/images/openmrs_logo_white.gif");
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
    var markCompletedDatePicker= jq( "#markCompletedDate" ).datepicker({
        onRender: function(date) {
            return date.valueOf() > now.valueOf() ? 'disabled' : '';
        },
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        markCompletedDatePicker.hide();
    }).data('datepicker');
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

    jq(".journalComment").keydown(
        function (event) {

            if (event.which == 13) {
                var journalID=(this.id).split("commentbox")[1];
                console.log(journalID);
                jq.get("commentBox/saveComment.action", {commentContent: this.value, parentId:journalID}, function(){
                    location.reload();
                });
                return false;
            }


        });
    jq("#statusUpdaterButton").click(
        function () {
            jq.get("statusUpdater/savePost.action", {title: jq("#statusUpdaterTitle").val(), content:jq("#statusUpdaterContent").val()}, function(){
                location.reload();
            });
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

    jq("#addrelationshipbutton").click(
        function () {
          jq.get("addRelationship/addRelationshipfromForm.action", {given:  jq("#givenpersonName").val(),family:  jq("#familypersonName").val(),gender:jq("#genderSelect").val(),personEmail:jq("#personEmail").val(),personRelationType:jq("#addRelationshipSelect").val(),securityLayerType:jq("#addRelationSecurityLevels").val()}, function(){
            });
            setTimeout(function(){
                location.reload();
            }, 3000);
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
    $('#saveGeneralHistorybutton').click(
        function () {
            jq.get("genHistoryModal/saveGenHistoryForm.action", {encounterId: jq("#genHistEncounterHolder").val(), cancerType:  jq("#genHistoryCancerTypeSelect").val(),cancerStage:  jq("#genHistoryCancerStageSelect").val(),cancerDate:jq("#genHistoryDate").val(),cancerAbnormalityBool:jq("#genHistoryCancerabnormalitySelect").val(),cancerAbnormalityType:jq("#genHistoryCancerabnormalityTypeSelect").val(),genHistoryCancerPcpName:jq("#genHistoryCancerPcpName").val(),genHistoryCancerPcpEmail:jq("#genHistoryCancerPcpEmail").val(),genHistoryCancerPcpPhone:jq("#genHistoryCancerPcpPhone").val()}, function(){
            });
            location.reload();
        });

    var surgerydatePicker= jq( "#surgeryDate" ).datepicker({
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        surgerydatePicker.hide();
    }).data('datepicker');
    $('.editSurgeryButton').click(
        function () {
          var encounterID=this.id;
            $("#surgeryEncounterHolder").val(encounterID);
            var surgeryTypeList=[];
            $('.'+encounterID+'surgeryType').each(function() {
                surgeryTypeList.push(($( this ).attr('id').split('surgeryType')[1]));
            });
            $('.surgeryTypesInModal').each(function() {
                if ( $.inArray(($( this ).val()).split('split')[1], surgeryTypeList)>-1) {
                    $(this).prop('checked', true);
                }
            });

            if($('#'+encounterID+'surgeryComplications').text() != null || $('#'+encounterID+'surgeryComplications').text() != ''){
                $('#majorComplicationsBoolSelect').val('1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA');
                $('#majorComplicationsTypeAnswer').val($('#'+encounterID+'surgeryComplications').text());
            }
            else{
                $('#majorComplicationsBoolSelect').val('1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA');
            }
            $("#surgeryDate").val($.datepicker.formatDate('mm/dd/yy', new Date($('#'+encounterID+'surgeryDate').text())));
           // console.log($('#'+encounterID+'surgeryPCPName').text());
            $("#surgeonPcpName").val($('#'+encounterID+'surgeryPCPName').text());
            $("#surgeonPcpEmail").val($('#'+encounterID+'surgeryPCPEmail').text());
            $("#surgeonPcpPhone").val($('#'+encounterID+'surgeryPCPPhone').text());

            $("#surgeryInstitutionName").val($('#'+encounterID+'surgeryinstituteName').text());
            $("#surgeryInstitutionCity").val($('#'+encounterID+'surgeryCity').text());
            $("#surgeryInstitutionState").val($('#'+encounterID+'surgeryState').text());
        });

    $('#saveSurgeryButton').click(
        function () {
            var surgeryTypeList = '';
            $('.surgeryTypesInModal').each(function () {
                if ($(this).is(':checked')) {
                     surgeryTypeList = surgeryTypeList + ($(this).val().split('split')[0]) + "split";
                }
            });
            if (jq("#surgeryEncounterHolder").val() == null || jq("#surgeryEncounterHolder").val() == '') {
            jq.get("surgeriesModal/saveNewSurgeryForm.action", {
                surgeryTypes: surgeryTypeList,
                surgeryComplications: jq("#majorComplicationsBoolSelect").val(),
                majorComplicationsTypeAnswer: jq("#majorComplicationsTypeAnswer").val(),
                surgeryDate: jq("#surgeryDate").val(),
                surgeonPcpName: jq("#surgeonPcpName").val(),
                surgeonPcpEmail: jq("#surgeonPcpEmail").val(),
                surgeonPcpPhone: jq("#surgeonPcpPhone").val(),
                surgeryInstitutionName: jq("#surgeryInstitutionName").val(),
                surgeryInstitutionCity: jq("#surgeryInstitutionCity").val(),
                surgeryInstitutionState: jq("#surgeryInstitutionState").val()
            }, function () {
               });
            setTimeout(
                function () {
                    location.reload();
                }, 2000);
        }
             else {
        jq.get("surgeriesModal/saveSurgeryForm.action", {
            encounterId: jq("#surgeryEncounterHolder").val(),
            surgeryTypes: surgeryTypeList,
            surgeryComplications: jq("#majorComplicationsBoolSelect").val(),
            majorComplicationsTypeAnswer: jq("#majorComplicationsTypeAnswer").val(),
            surgeryDate: jq("#surgeryDate").val(),
            surgeonPcpName: jq("#surgeonPcpName").val(),
            surgeonPcpEmail: jq("#surgeonPcpEmail").val(),
            surgeonPcpPhone: jq("#surgeonPcpPhone").val(),
            surgeryInstitutionName: jq("#surgeryInstitutionName").val(),
            surgeryInstitutionCity: jq("#surgeryInstitutionCity").val(),
            surgeryInstitutionState: jq("#surgeryInstitutionState").val()
        }, function () {
             });
        setTimeout(
            function () {
                location.reload();
            }, 2000);
    }
        });

    var chemotherapyStartdatePicker= jq( "#chemoStartDate" ).datepicker({
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        chemotherapyStartdatePicker.hide();
    }).data('datepicker');
    var chemotherapyEnddatePicker= jq( "#chemoEndDate" ).datepicker({
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        chemotherapyEnddatePicker.hide();
    }).data('datepicker');
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

    $('#saveChemotherapyButton').click(
        function () {
            var chemotherapyMedTypesList='';
            $('.chemotherapyMedTypesInModal').each(function() {
                if ( $(this).is(':checked')) {
                    chemotherapyMedTypesList=chemotherapyMedTypesList+($( this ).val().split('split')[0])+"split";
                }
            });
            if(jq("#chemotherapyEncounterHolder").val() == null || jq("#chemotherapyEncounterHolder").val() == '') {
                jq.get("chemotherapyModal/saveNewChemotherapyForm.action", {
                    chemotherapyMeds: chemotherapyMedTypesList,
                    centralLine: jq("#centralLineBoolSelect").val(),
                    chemoStartDate: jq("#chemoStartDate").val(),
                    chemoEndDate: jq("#chemoEndDate").val(),
                    chemotherapyPcpName: jq("#oncologistPcpName").val(),
                    chemotherapyPcpEmail: jq("#oncologistPcpEmail").val(),
                    chemotherapyPcpPhone: jq("#oncologistPcpPhone").val(),
                    chemotherapyInstitutionName: jq("#chemotherapyInstitutionName").val(),
                    chemotherapyInstitutionCity: jq("#chemotherapyInstitutionCity").val(),
                    chemotherapyInstitutionState: jq("#chemotherapyInstitutionState").val()
                }, function () {
                  });
                setTimeout(
                    function () {
                        location.reload();
                    }, 2000);
            }
             else {
                jq.get("chemotherapyModal/saveChemotherapyForm.action", {
                    encounterId: jq("#chemotherapyEncounterHolder").val(),
                    chemotherapyMeds: chemotherapyMedTypesList,
                    centralLine: jq("#centralLineBoolSelect").val(),
                    chemoStartDate: jq("#chemoStartDate").val(),
                    chemoEndDate: jq("#chemoEndDate").val(),
                    chemotherapyPcpName: jq("#oncologistPcpName").val(),
                    chemotherapyPcpEmail: jq("#oncologistPcpEmail").val(),
                    chemotherapyPcpPhone: jq("#oncologistPcpPhone").val(),
                    chemotherapyInstitutionName: jq("#chemotherapyInstitutionName").val(),
                    chemotherapyInstitutionCity: jq("#chemotherapyInstitutionCity").val(),
                    chemotherapyInstitutionState: jq("#chemotherapyInstitutionState").val()
                }, function () {
                });
                setTimeout(
                    function () {
                        location.reload();
                    }, 2000);
            }
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

    $('#saveRadiationButton').click(
        function () {
            var radiationTypesList = '';
            $('.radiationTypesInModal').each(function () {
                if ($(this).is(':checked')) {
                    radiationTypesList = radiationTypesList + ($(this).val().split('split')[0]) + "split";
                }
            });
           if (jq("#radiationEncounterHolder").val() == null || jq("#radiationEncounterHolder").val() == '') {
            jq.get("radiationModal/saveNewRadiationForm.action", {
                radiationTypes: radiationTypesList,
                radiationStartDate: jq("#radiationStartDate").val(),
                radiationEndDate: jq("#radiationEndDate").val(),
                radiationPcpName: jq("#radiologistPcpName").val(),
                radiationPcpEmail: jq("#radiologistPcpEmail").val(),
                radiationPcpPhone: jq("#radiologistPcpPhone").val(),
                radiationInstitutionName: jq("#radiologistInstitutionName").val(),
                radiationInstitutionCity: jq("#radiologistInstitutionCity").val(),
                radiationInstitutionState: jq("#radiologistInstitutionState").val()
            }, function () {
            });
            setTimeout(
                function () {
                    location.reload();
                }, 2000);
        }
        else
    {
        jq.get("radiationModal/saveRadiationForm.action", {
            encounterId: jq("#radiationEncounterHolder").val(),
            radiationTypes: radiationTypesList,
            radiationStartDate: jq("#radiationStartDate").val(),
            radiationEndDate: jq("#radiationEndDate").val(),
            radiationPcpName: jq("#radiologistPcpName").val(),
            radiationPcpEmail: jq("#radiologistPcpEmail").val(),
            radiationPcpPhone: jq("#radiologistPcpPhone").val(),
            radiationInstitutionName: jq("#radiologistInstitutionName").val(),
            radiationInstitutionCity: jq("#radiologistInstitutionCity").val(),
            radiationInstitutionState: jq("#radiologistInstitutionState").val()
        }, function () {
             });
        setTimeout(
            function () {
                location.reload();
            }, 2000);
    }
        });

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

        });

    //------------------- Edit Relation Button JS Ends ----------------------


//------------- Edit Relation Button save JS ---------
    $('#editRelationshipSaveButton').click(
        function () {
            jq.get("connections/saveRelationshipfromEdit.action", {relationshipId:  jq("#editRelationshipIdHolder").val(),personRelationType:  jq("#editRelationshipSelect").val(),personRelationSecurityLayer:jq("#editRelationSecurityLevels").val()}, function(){
            });
            setTimeout(function(){
                location.reload();
            }, 2000);
        });

    //------------------- Edit Relation Button save JS Ends -----

    //------------- accept connection request Button JS ----------------
    $('.acceptConnectionRequest').click(
        function () {
            var relationId = this.id.split("acceptConnectionRequest")[0];
            jq.get("connections/acceptConnectionRequest.action", {relationshipId: relationId}, function(){
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

    $('#saveuserprofile').click(
        function () {
                jq.get("profileEdit/saveProfileEditForm.action", {
                    personId: jq("#personIdHolder").val(),
                    givenName: jq("#userprofileGivenName").val(),
                    familyName: jq("#userprofileFamilyName").val(),
                    gender: jq("#userprofileGenderSelect").val(),
                    birthDate: jq("#userprofileDOB").val(),
                }, function () {
                });
                setTimeout(
                    function () {
                        $('#alertContainer').html( "<div class='alert alert-dismissible alert-info'> <button type='button' class='close' data-dismiss='alert'>&times;</button> <strong>Message from Admin:</strong> Your Profile information has been saved!  please <a href="+OpenMRSInstance.split('/patientportaltoolkit')[0]+'/logout'+ " class='alert-link'>logout</a> and log back in to see your changes applied </div>");
                    }, 500);


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
                console.log(relationitem);
            });
        }) ).then(function() {
        console.log(listOfRelationsData);
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
                    }, 2000);
        });

    //------------------- compose message JS ENDS ----------------------

    //------------------- Follow up care JS ----------------------

    //JS for the Button Events

  /*  $('.markCompletedReminder').click(
        function () {
            var reminderID=this.id.split("markCompletedReminder")[1];
           // alert(this.id);
            jq.get("appointments/markCompleted.action", {
                reminderId: reminderID,
            }, function () {
            });
            setTimeout(
                function () {
                    location.reload();
                }, 2000);
        });*/

    $('.markCompletedReminder').click(
        function () {
            var reminderID=this.id.split("markCompletedReminder")[1];
            $('#markCompletedIdHolder').val(reminderID);
        });
    $('#saveMarkCompletedButton').click(
        function () {
            jq.get("appointments/markCompleted.action", {reminderId: jq("#markCompletedIdHolder").val(), markCompletedDate: jq("#markCompletedDate").val(), doctorName:  jq("#markCompletedDoctor").val(),comments:  jq("#markCompletedComments").val()}, function(){
            });
            location.reload();
        });

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

    // JS for Feedback Form

    $('#sendFeedback').click(
        function () {
            if(jq("#feedbacktextdata").val() != null || jq("#feedbacktextdata").val() != '') {
                jq.get("feedback/sendFeedback.action", {
                    feedbackMessage: jq("#feedbacktextdata").val()
                }, function () {
                });
                setTimeout(
                    function () {
                        location.reload();
                    }, 2000);
            }
        });

    // make all items having class 'edit' editable
    $('.edit').editable();

    $('[data-toggle="tooltip"]').tooltip();


    $('.mycancerbuddies').click(
        function () {
            $('#mycancerbuddiesSave').removeClass('disabled');
            $('#mycancerbuddiesSave').prop('disabled', false);
        });

  /*  $('.remindertriggerdate').click(
        function () {
            $('#remindertriggerdateSave').removeClass('disabled');
            $('#remindertriggerdateSave').prop('disabled', false);
        });

    $('#remindertriggerdateSave').click(
        function () {
            if(jq("#remindertriggerdatevalue").text() != null ) {
                jq.get("treatments/saveReminderTriggerDate.action", {
                    reminderTriggerDate: jq("#remindertriggerdatevalue").text()
                }, function () {
                });
                setTimeout(
                    function () {
                        location.reload();
                    }, 1000);
            }
        });*/

});