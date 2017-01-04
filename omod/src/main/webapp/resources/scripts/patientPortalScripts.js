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

    var dataaa=[
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
    ];
    function foo () {
        //hardcoded data to be modified
        $.get("http://localhost:8081/openmrs2/ws/patientportaltoolkit/getremindersforpatient/d99d2110-7cad-4282-9e00-ede06b92c965", function (reminderData) {
            //alert(dataa);
            $('#calendar').data('calendar').setDataSource(reminderData);
        });
    }
    var calendar= $('#preventativeCareCalendar').calendar({
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
    var calendar= $('#calendar').calendar({
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

    setTimeout(foo, 1000);
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
    // JS for The Tool
    var Bata=[
        {"CEA Tests":
            [
                {
                    "date": "3 Apr 2016",
                    "completedDate": null,
                    "type": "CEA Test"
                },
                {
                    "date": "9 Nov 2015",
                    "completedDate": "9 Nov 2015",
                    "type": "CEA Test"
                },
                {
                    "date": "5 Apr 2015",
                    "completedDate": "5 Apr 2015",
                    "type": "CEA Test"
                },
                {
                    "date": "9 Nov 2014",
                    "completedDate": null,
                    "type": "CEA Test"
                },
                {
                    "date": "12 Jun 2014",
                    "completedDate": "12 Jun 2014",
                    "type": "CEA Test"
                }
            ]
        },
        {"Colonoscopies":
            [
                {
                    "date": "9 Jul 2015",
                    "completedDate": null,
                    "type": "Colonoscopy"
                },
                {
                    "date": "15 Apr 2015",
                    "completedDate": null,
                    "type": "Colonoscopy"
                },
                {
                    "date": "13 Dec 2014",
                    "completedDate": "4 Dec 2014",
                    "type": "Colonoscopy"
                },
                {
                    "date": "9 Jul 2014",
                    "completedDate": "28 Jul 2014",
                    "type": "Colonoscopy"
                }
            ]
        },
        {"CT Scans":
            [
                {
                    "date": "9 Jun 2015",
                    "completedDate": "12 Jun 2015",
                    "type": "CT Scan"
                },
                {
                    "date": "5 Mar 2015",
                    "completedDate": "8 Apr 2015",
                    "type": "CT Scan"
                },
                {
                    "date": "9 Nov 2014",
                    "completedDate": "29 Nov 2014",
                    "type": "CT Scan"
                },
                {
                    "date": "9 Jun 2014",
                    "completedDate": "27 May 2014",
                    "type": "CT Scan"
                }
            ]
        }
    ]
    jq(function() {
        var svg = d3.select("div#chart").append("svg"),
            margin = {top: 80, right: 50, bottom: 50, left: 150},
            margin2 = {top: 50, right: 50, bottom: 20, left: 150},
            width = 900 - margin.left - margin.right,
            height = _.size(Bata) * margin.top - margin.top - margin.bottom,
            height2 = 30,
            itemHeight = 15,
            itemPadding = 15;

        var x = d3.time.scale().range([0, width]),
            x2 = d3.time.scale().range([0, width]);

        var xAxis = d3.svg.axis().scale(x).orient("top").tickSize(height).tickPadding(6),
            xAxis2 = d3.svg.axis().scale(x2).orient("bottom").ticks(width < 910 ? 8 : 12);

        // This is the thing that expands and contracts the timeline
        var brush = d3.svg.brush()
            .x(x2)
            .on("brush", brushed);

        svg.attr({
            "width": width + margin.left + margin.right,
            "height": height + margin.top + margin.bottom + height2 + margin2.top + margin2.bottom
        });

        svg.append("defs").append("clipPath")
            .attr("id", "clip-focus")
            .append("rect")
            .attr({
                "width": width + 15,
                "height": height,
                "x": -7
            });

        var focus = svg.append("g")
            .attr({
                "class": "focus",
                "transform": "translate(" + margin.left + "," + margin.top + ")"
            });

        var context = svg.append("g")
            .attr("class", "context")
            .attr("transform", "translate(" + margin2.left + "," + (margin2.top + 12 + margin.top + margin.bottom + height2) + ")");

        /*********** Go ahead and declare some of the variables to be used globally *************/
        var items;
        var itemAndValue;
        var cross = d3.svg.symbol().type("cross");
        var itemText;


        /*********** Data Transforms **************/
            // transform the dates into valid dates
        Bata = _.map(Bata, function(d, i) {
            return {
                name: _.keys(d)[0],
                dates: _.map(_.values(d)[0], function(item, index) {
                    return new Date(item["date"]);
                }),
                completedDates: _.map(_.values(d)[0], function(item, index) {
                    return new Date(item["completedDate"]);
                })
            }
        });
        // these will be used for showing the current date and setting the range for the context brush
        var today = new Date();
        var sixMosAgo = addMonths(new Date(), -6);
        var threeMosAgo = addMonths(new Date(), -3);
        var threeMosFromNow = addMonths(new Date(), +3);
        function addMonths(date, months) {
            date.setMonth(date.getMonth() + months);
            return date;
        }

        // make the valid dates readable when displaying on screen
        var dateFormat = d3.time.format("%d-%b-%Y");


        // take all dates from all arrays and get the first and last values
        var dateRange = _.sortBy(_.flatten(_.pluck(Bata, "dates")));
        // get the oldest date and add subtract a month (we don't want it to be hanging off the edge of the chart)
        var oldestDate = _.first(dateRange);
        // get the most recent date and add one month
        var mostRecentDate = _.last(dateRange);

        /************ Back to Business *************/
       // x.domain([oldestDate, mostRecentDate]);
        x.domain([mostRecentDate,oldestDate]); //this changed the order of dates
        x2.domain(x.domain());

        focus.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis);

        brush.extent([sixMosAgo, threeMosFromNow]);
        brush(d3.select(".brush").transition());
        brushed();

        // make a line showing today
        var todayLine = focus.append("rect")
            .attr({
                "class": "today-line",
                "x": x(today),
                "y": 0,
                "width": "2px",
                "height": height,
                "clip-path": "url(#clip-focus)"
            })
            .style({
                "fill": "#f17",
                "fill-opacity": 0.5
            });

        // Context and Brush
        drawContext(dateRange);
        // item group
        drawItems(Bata);

        function drawContext(data) {
            // small axis
            context.append("g")
                .attr("class", "x axis")
                .call(xAxis2);

            var miniCircles = context.selectAll("circle")
                .data(data)
                .enter().append("circle")
                .attr({
                    "class": "circle",
                    "transform": "translate(0, -15)",
                    "cx": function(d) { return x2(d); },
                    "r": "2px"
                });

            // range brush
            context.append("g")
                .attr("class", "x brush")
                .call(brush)
                .selectAll("rect")
                .attr("y", -31)
                .attr("height", height2);
        }

        function drawItems(data) {

            _.map(data, function(itemData, i) {

                var itemMargin = i == 0 ? 15 : 15 + (i * (itemPadding + itemHeight));

                // create an item group (title, crosses, and other stuff)
                items = focus.append("g")
                    .attr({
                        "key": i,
                        "class": "item",
                        "transform": "translate(0," + (height - itemHeight - itemMargin) + ")"
                    });

                // the item name (e.g., Colonoscopy)
                items.append("text")
                    .attr({
                        "class": "item-title",
                        "x": -20,
                        "y": 14
                    })
                    .text(_.values(itemData)[0]);


                // to clip the item group from extending beyond the edges of the timeline
                var itemGroup = items.append("g")
                    .attr({
                        "key": i,
                        "clip-path": "url(#clip-focus)"
                    });

                //the height area for items
                itemGroup.append("rect")
                    .attr({
                        "class": "total-range",
                        "width": width,
                        "height": itemHeight
                    });


                // the item crosses
                itemAndValue = itemGroup.selectAll("g")
                    .data(_.values(itemData)[1])
                    .enter().append("g")
                    .attr({
                        "class": "item-and-value",
                        "transform": function(d) {
                            return "translate(" + x(d) + "," + (itemHeight/2) + ")"
                        }
                    });

                itemAndValue.append("path")
                    .attr({
                        "class": function(d) {
                            if(d > threeMosAgo && d < threeMosFromNow && d["completedDate"] == null) {
                                return "cross alert"
                            } else if(d["completedDate"] != null) {
                                return "cross completed"
                            } else {
                                return "cross"
                            }
                        },
                        "d": cross
                    });

                // here is the text for each cross with rect background
                var itemText = itemAndValue.append("g")
                    .attr({
                        "class": "item-text",
                        "transform": "scale(0)"
                    });

                itemText.append("rect")
                    .attr({
                        "class": "item-text-background",
                        "width": 100,
                        "height": itemHeight + itemPadding,
                        "y": -itemHeight,
                        "x": function(d) {
                            if(x(d) >= width - 120) {
                                return -102
                            } else {
                                return 2
                            }
                        }
                    });

                itemText.append("text")
                    .attr({
                        "x": function(d) {
                            if(x(d) >= width - 120) {
                                return -10
                            } else {
                                return 10
                            }
                        },
                        "y": 8
                    })
                    .style({
                        "text-anchor": function(d) {
                            if(x(d) >= width - 120) {
                                return "end"
                            } else {
                                return "start"
                            }
                        }
                    })
                    .text(function(d, i) { return dateFormat(itemData["dates"][i]) });

            });
        }

        function brushed() {
            x.domain(brush.empty() ? x2.domain() : brush.extent());
            focus.select(".today-line").attr({
                "x": x(today)
            });
            focus.selectAll(".item-and-value").attr({
                "transform": function(d) {
                    return "translate(" + x(d) + "," + (itemHeight/2) + ")"
                }
            });
            focus.selectAll(".x.axis").call(xAxis);
        }

        d3.selectAll(".item-and-value").each(function() {
            var itemNode = d3.select(this);

            function mousemove() {
                d3.mouse(itemNode.node());
            }

            d3.select("div#chart").on("mousemove", mousemove);
        });


    });

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
});