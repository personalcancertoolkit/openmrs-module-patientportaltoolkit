/**
 * Created by Maurya on 22/06/2015.
 */
jq = jQuery;

//////////////
// Initialize DOM elements and their respective handlers
//////////////
jq(document).ready(function() {

    const SecurityLayer = {
        CAN_SEE_POSTS: 'c21b5749-5972-425b-a8dc-15dc8f899a96',
        CAN_SEE_MEDICAL_PROFILE: '18e440a6-518b-4dbd-8057-dd0f88ee6d15'
    };

    // setting defaults for the editable
    jq.fn.editable.defaults.mode = 'inline';
    jq.fn.editable.defaults.showbuttons = true;
    jq.fn.editable.defaults.type = 'text';

    const baseUrl = window.location.href.split("/patientportaltoolkit")[0];
    jq("#navigationLogout").attr("href", baseUrl + "/logout");
    jq(".imagePlaceHolders").attr("src", baseUrl + "/images/openmrs_logo_white.gif");
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

    var markScheduledDatePicker = jq("#markScheduledDate").datepicker({
        onRender: function(date) {
            return date.valueOf() < now.valueOf() ? 'disabled' : '';
        },
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        markScheduledDatePicker.hide();
    }).data('datepicker');
    var userprofileDOBDatePicker = jq("#userprofileDOB").datepicker({
        onRender: function(date) {
            return date.valueOf() > now.valueOf() ? 'disabled' : '';
        },
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        userprofileDOBDatePicker.hide();
    }).data('datepicker');

    jq(".reformatText").each(function() {
        var str = jq(this).text().toString();
        var newStr = str[0].toUpperCase() + str.slice(1).toLowerCase();
        jq(this).text(newStr);
    });

    // Automatically return the user to the last tab they visited on the 
    // Medical Profile
    jq(function() {
        jq('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
            localStorage.setItem('lastTab', jq(this).attr('href'));
        });

        var lastTab = localStorage.getItem('lastTab');

        // Got to a specific tab
        const url = window.location.href;
        const [pageUrl, possibleTab] = url.split("#");
        
        const medicalProfileTabList = [
            "treatments",
            "sideEffects",
            "followUpCare",
            "community",
            "symptomManagement",
            "preventiveCare"
        ];

        // Prefer a direct link to a tab over showing the lastTab
        if(possibleTab && medicalProfileTabList.includes(possibleTab)) {
            jq('[href="' + "#" + possibleTab + '"]').tab('show');
            
            // Remove the #tab from page history so that on page reload
            // the correct "last tab" will not get squashed by this one
            // repeating
            window.history.replaceState(null, "", pageUrl);
        }
        else if (lastTab) {
            jq('[href="' + lastTab + '"]').tab('show');
        }
    });

    jq(".cancelModal").click(
        function() {
            location.reload();
        });
    jq(".editGenHistButton").click(
        function() {
            var encounterID = this.id;
            logEvent('clicked_GenHistoryButton', JSON.stringify({
                'genHistoryEncounterId': encounterID
            }));
            jq("#genHistEncounterHolder").val(encounterID);
            const dd = document.getElementById('genHistoryCancerTypeSelect');
            dd.selectedIndex = [...dd.options].findIndex(option => option.text === jq('#' + encounterID + 'cancerType').text());
            const dd2 = document.getElementById('genHistoryCancerStageSelect');
            dd2.selectedIndex = [...dd2.options].findIndex(option => option.text === jq('#' + encounterID + 'cancerStage').text());
            if (jq('#' + encounterID + 'diagnosisDate').text())
                jq("#genHistoryDate").val(jq.datepicker.formatDate('mm/dd/yy', new Date(jq('#' + encounterID + 'diagnosisDate').text())));
            if (jq('#' + encounterID + 'genHistoryCancerPcpName').text() != null && jq('#' + encounterID + 'genHistoryCancerPcpName').text() != '' && jq('#' + encounterID + 'genHistoryCancerPcpName').text() != 'null') {
                jq("#genHistoryCancerPcpName").val(jq('#' + encounterID + 'genHistoryCancerPcpName').text())
            };
            if (jq('#' + encounterID + 'genHistoryCancerPcpEmail').text() != null && jq('#' + encounterID + 'genHistoryCancerPcpEmail').text() != '' && jq('#' + encounterID + 'genHistoryCancerPcpEmail').text() != 'null') {
                jq("#genHistoryCancerPcpEmail").val(jq('#' + encounterID + 'genHistoryCancerPcpEmail').text())
            };
            if (jq('#' + encounterID + 'genHistoryCancerPcpPhone').text() != null && jq('#' + encounterID + 'genHistoryCancerPcpPhone').text() != '' && jq('#' + encounterID + 'genHistoryCancerPcpPhone').text() != 'null') {
                jq("#genHistoryCancerPcpPhone").val(jq('#' + encounterID + 'genHistoryCancerPcpPhone').text())
            };
        });


    jq('.addGenHistButton').click(
        function() {
            jq("#genHistPatientUuidHolder").val(jq("#treatmentsPatientUuidHolder").val());
        });
    
    jq('.editSurgeryButton').click(
        function() {
            var encounterID = this.id;
            logEvent('clicked_SurgeryButton', JSON.stringify({
                'surgeryEncounterId': encounterID
            }));
            jq("#surgeryEncounterHolder").val(encounterID);
            var surgeryTypeList = [];
            jq('.' + encounterID + 'surgeryType').each(function() {
                surgeryTypeList.push((jq(this).attr('id').split('surgeryType')[1]));
            });
            jq('.surgeryTypesInModal').each(function() {
                if (jq.inArray((jq(this).val()).split('split')[1], surgeryTypeList) > -1) {
                    jq(this).prop('checked', true);
                }
            });
            if (jq('#' + encounterID + 'surgeryDate').text().trim()) {
                jq("#surgeryDate").val(jq.datepicker.formatDate('mm/dd/yy', new Date(jq('#' + encounterID + 'surgeryDate').text())));
            }
            
            jq("#surgeonPcpName").val(jq('#' + encounterID + 'surgeryPCPName').text());
            jq("#surgeonPcpEmail").val(jq('#' + encounterID + 'surgeryPCPEmail').text());
            jq("#surgeonPcpPhone").val(jq('#' + encounterID + 'surgeryPCPPhone').text());

            jq("#surgeryInstitutionName").val(jq('#' + encounterID + 'surgeryinstituteName').text());
            jq("#surgeryInstitutionCity").val(jq('#' + encounterID + 'surgeryCity').text());
            jq("#surgeryInstitutionState").val(jq('#' + encounterID + 'surgeryState').text());
            jq("#otherSurgeryName").val(jq('#' + encounterID + 'otherSurgeryName').text());
        });

    jq('.addSurgeryButton').click(
        function() {
            jq("#surgeryPatientUuidHolder").val(jq("#treatmentsPatientUuidHolder").val());
            jq("#surgeryEncounterHolder").val('');
            jq('.surgeryTypesInModal').each(function() {
                jq(this).prop('checked', false);
            });
            jq("#majorComplicationsBoolSelect").val('');
            jq("#majorComplicationsTypeAnswer").val('');
            jq("#surgeryDate").val('');
            jq("#surgeonPcpName").val('');
            jq("#surgeonPcpEmail").val('');
            jq("#surgeonPcpPhone").val('');
            jq("#surgeryInstitutionName").val('');
            jq("#surgeryInstitutionCity").val('');
            jq("#surgeryInstitutionState").val('');
        });

    jq('.editChemotherapyButton').click(
        function() {
            var encounterID = this.id;
            logEvent('clicked_ChemotherapyyButton', JSON.stringify({
                'chemotherapyEncounterId': encounterID
            }));
            jq("#chemotherapyEncounterHolder").val(encounterID);
            var chemotherapyMedList = [];
            
            jq('.' + encounterID + 'chemotherapymed').each(function() {                
                chemotherapyMedList.push((jq(this).attr('id').split('chemotherapymed')[1]));
            });
            jq('.chemotherapyMedTypesInModal').each(function() {
                if (jq.inArray((jq(this).val()).split('split')[1], chemotherapyMedList) > -1) {
                    jq(this).prop('checked', true);
                }
            });

            if (jq('#' + encounterID + 'centralLine').text() == "Yes") {
                jq('#centralLineBoolSelect').val("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            } else {
                jq('#centralLineBoolSelect').val("1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
            if (jq('#' + encounterID + 'chemotherapyStartDate').text())
                jq('#chemoStartDate').val(jq.datepicker.formatDate('mm/dd/yy', new Date(jq('#' + encounterID + 'chemotherapyStartDate').text())));
            if (jq('#' + encounterID + 'chemotherapyEndDate').text())
                jq('#chemoEndDate').val(jq.datepicker.formatDate('mm/dd/yy', new Date(jq('#' + encounterID + 'chemotherapyEndDate').text())));
            jq("#oncologistPcpName").val(jq('#' + encounterID + 'chemotherapyPCPName').text());
            jq("#oncologistPcpEmail").val(jq('#' + encounterID + 'chemotherapyPCPEmail').text());
            jq("#oncologistPcpPhone").val(jq('#' + encounterID + 'chemotherapyPCPPhone').text());

            jq("#chemotherapyInstitutionName").val(jq('#' + encounterID + 'chemotherapyinstituteName').text());
            jq("#chemotherapyInstitutionCity").val(jq('#' + encounterID + 'chemotherapyCity').text());
            jq("#chemotherapyInstitutionState").val(jq('#' + encounterID + 'chemotherapyState').text());
            jq("#otherChemotherapyMedicationName").val(jq('#' + encounterID + 'otherChemotherapyMedicationName').text());
        });

    jq('.addChemotherapyButton').click(
        function() {
            jq("#chemotherapyPatientUuidHolder").val(jq("#treatmentsPatientUuidHolder").val());
            jq("#chemotherapyEncounterHolder").val('');
            jq('.chemotherapyMedTypesInModal').each(function() {
                jq(this).prop('checked', false);
            });
            jq("#centralLineBoolSelect").val('');
            jq("#chemoStartDate").val('');
            jq("#chemoEndDate").val('');
            jq("#oncologistPcpName").val('');
            jq("#oncologistPcpEmail").val('');
            jq("#oncologistPcpPhone").val('');
            jq("#chemotherapyInstitutionName").val('');
            jq("#chemotherapyInstitutionCity").val('');
            jq("#chemotherapyInstitutionState").val('');
        });


    var radiationStartdatePicker = jq("#radiationStartDate").datepicker({
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        radiationStartdatePicker.hide();
    }).data('datepicker');
    var radiationEnddatePicker = jq("#radiationEndDate").datepicker({
        format: 'mm/dd/yyyy'
    }).on('changeDate', function() {
        radiationEnddatePicker.hide();
    }).data('datepicker');

    jq('.editRadiationButton').click(
        function() {
            var encounterID = this.id;
            logEvent('clicked_RadiationButton', JSON.stringify({
                'radiationEncounterId': encounterID
            }));
            jq("#radiationEncounterHolder").val(encounterID);
            var radiationTypesList = [];
            jq('.' + encounterID + 'radiationType').each(function() {
                radiationTypesList.push((jq(this).attr('id').split('radiationType')[1]));
            });
            jq('.radiationTypesInModal').each(function() {
                if (jq.inArray((jq(this).val()).split('split')[1], radiationTypesList) > -1) {
                    jq(this).prop('checked', true);
                }
            });
            if (jq('#' + encounterID + 'radStartDate').text())
                jq('#radiationStartDate').val(jq.datepicker.formatDate('mm/dd/yy', new Date(jq('#' + encounterID + 'radStartDate').text())));
            if (jq('#' + encounterID + 'radEndDate').text())
                jq('#radiationEndDate').val(jq.datepicker.formatDate('mm/dd/yy', new Date(jq('#' + encounterID + 'radEndDate').text())));
            jq("#radiologistPcpName").val(jq('#' + encounterID + 'radPCPName').text());
            jq("#radiologistPcpEmail").val(jq('#' + encounterID + 'radPCPEmail').text());
            jq("#radiologistPcpPhone").val(jq('#' + encounterID + 'radPCPPhone').text());

            jq("#radiologistInstitutionName").val(jq('#' + encounterID + 'radinstituteName').text());
            jq("#radiologistInstitutionCity").val(jq('#' + encounterID + 'radCity').text());
            jq("#radiologistInstitutionState").val(jq('#' + encounterID + 'radState').text());
        });

    jq('.addRadiationButton').click(
        function() {
            jq("#radiationPatientUuidHolder").val(jq("#treatmentsPatientUuidHolder").val());
            jq("#radiationEncounterHolder").val('');
            jq('.radiationTypesInModal').each(function() {
                jq(this).prop('checked', false);
            });
            jq("#radiationStartDate").val('');
            jq("#radiationEndDate").val('');
            jq("#radiologistPcpName").val('');
            jq("#radiologistPcpEmail").val('');
            jq("#radiologistPcpPhone").val('');
            jq("#radiologistInstitutionName").val('');
            jq("#radiologistInstitutionCity").val('');
            jq("#radiologistInstitutionState").val('');
        });
    jq(".deleteTreatmentButton").click(
        function() {
            jq("#treatmentEncounterHolder").val(this.id);
        });
    jq("#deleteTreatmentButton").click(
        function() {
            jq.get("treatments/deleteTreatment.action", {
                treatmentId: jq("#treatmentEncounterHolder").val()
            }, function() {
                location.reload();
            });
        });
    jq(".removeRelationCloseButton").click(
        function() {
            jq("#remove-relationId").val(this.id.split("removeRelation")[1]);
        });
    jq("#relation-delete-btn").click(
        function() {
            jq.get("connections/removeRelationship/removeRelationship.action", {
                relationshipId: jq("#remove-relationId").val()
            }, function() {
                location.reload();
            });
        });

    //------------- Add Relation Button save JS ---------

    jq("#addrelationshipbutton").click(
        function() {
            var checkboxValuesList = [];
            jq(".addRelationShareCheckbox:checkbox:checked").each(function() {
                checkboxValuesList.push(jq(this).val());
                //alert(checkboxValues);
                //checkboxValues=checkboxValues+$(this).val()+",";
            });
            var checkboxValues = checkboxValuesList.toString();
            if (checkboxValuesList.length !== 0) { 
                jq.ajax({
                    type: "GET",
                    url: "connections/addRelationship/addRelationshipfromForm.action",
                    data: {
                        given: jq("#givenpersonName").val(),
                        family: jq("#familypersonName").val(),
                        gender: jq("#genderSelect").val(),
                        personEmail: jq("#personEmail").val(),
                        personRelationType: jq("#addRelationshipSelect").val(),
                        securityLayerType: checkboxValues
                    },
                    success: function() {
                        jq("#connectionSaveSuccess").show();
                        setTimeout(function() {
                            location.reload();
                        }, 4000);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        alert('Error: '+ errorThrown);
                    },
                });
            } else {
                alert('Please choose at least one access level for your new connection');
            }
        });
    //------------- Add Relation Button save JS Ends ---------

    //------------- Edit Relation Button JS ---------
    jq('.editRelationButton').click(
        function() {
            var relationshipID = this.id.split('relationedit')[1];
            jq("#editRelationshipIdHolder").val(relationshipID);

            const editRelationshipModal = jq("#edit-relationship-modal");
            const editRelationshipSelect = editRelationshipModal.find("#editRelationshipSelect");
            const canSeeMyPostsCheckbox = editRelationshipModal.find('.editRelationShareCheckbox[data-security-layer-description="Posts"]');

            jq("#editPersonName").text(jq('#' + relationshipID + 'relationPerson').text());
            jq("#editRelationProfileBadge").text(jq('#' + relationshipID + 'relationPerson').text().match(/\b(\w)/g).join(''));
            editRelationshipSelect.val(jq('#' + relationshipID + 'relationType').text());
            jq("#editRelationSecurityLevels").val(jq('#' + relationshipID + 'relationShareID').val());
            if (jq('#checkPersonInRelation' + relationshipID).val() == "0") {
                editRelationshipSelect.attr('disabled', true);
            }

            jq.ajax({
                type: "POST",
                url: baseUrl + "/ws/patientportaltoolkit/hasaccess",
                data: {
                    relationshipId: relationshipID,
                    shareType: "6776d050-e2fe-47cc-8af4-de3fdeb1b76d"
                },
                success: function setChecked(response) {
                    if (response) {
                        jq("#editShareType" + "6776d050-e2fe-47cc-8af4-de3fdeb1b76d").prop('checked', true);
                    } else {
                        jq("#editShareType" + "6776d050-e2fe-47cc-8af4-de3fdeb1b76d").prop('checked', false);
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert('Error: '+ errorThrown);
                },
            });
            jq.ajax({
                type: "POST",
                url: baseUrl + "/ws/patientportaltoolkit/hasaccess",
                data: {
                    relationshipId: relationshipID,
                    shareType: SecurityLayer.CAN_SEE_MEDICAL_PROFILE
                },
                success: function setChecked(response) {
                    if (response) {
                        jq("#editShareType" + SecurityLayer.CAN_SEE_MEDICAL_PROFILE).prop('checked', true);
                    } else {
                        jq("#editShareType" + SecurityLayer.CAN_SEE_MEDICAL_PROFILE).prop('checked', false);
                    }
                },                
                error: function(jqXHR, textStatus, errorThrown) {
                    alert('Error: '+ errorThrown);
                },
            });
            jq.ajax({
                type: "POST",
                url: baseUrl + "/ws/patientportaltoolkit/hasaccess",
                data: {
                    relationshipId: relationshipID,
                    shareType: SecurityLayer.CAN_SEE_POSTS
                },
                success: function setChecked(response) {
                    if (response) {
                        jq("#editShareType" + SecurityLayer.CAN_SEE_POSTS).prop('checked', true);
                    } else {
                        jq("#editShareType" + SecurityLayer.CAN_SEE_POSTS).prop('checked', false);
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert('Error: '+ errorThrown);
                },
            });

            if (editRelationshipSelect.children("option:selected").text() === "Doctor") {
                canSeeMyPostsCheckbox.prop("checked", false).prop("disabled", true).parents(".form-check").hide();
            } else {
                canSeeMyPostsCheckbox.prop("disabled", false).parents(".form-check").show();
            }
        });

    //------------------- Edit Relation Button JS Ends ----------------------


    //------------- Edit Relation Button save JS ---------
    jq('#editRelationshipSaveButton').click(
        function() {
            var checkboxValuesList = [];
            jq(".editRelationShareCheckbox:checkbox:checked").each(function() {
                checkboxValuesList.push(jq(this).val());
                //checkboxValues=checkboxValues+$(this).val()+",";
            });
            if (checkboxValuesList.length !== 0) {
                
                var checkboxValues = checkboxValuesList.toString();
                jq.ajax({
                    type: "POST",
                    url: "connections/connections/saveRelationshipfromEdit.action",
                    data: {
                        relationshipId: jq("#editRelationshipIdHolder").val(),
                        personRelationType: jq("#editRelationshipSelect").val(),
                        personRelationSecurityLayer: checkboxValues
                    },
                    success: function() {
                        location.reload();
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        alert('Error: '+ errorThrown);
                    },
                });
            } else {
                alert('Please choose at least one access level for this connection');
            }
            
        });

    //------------------- Edit Relation Button save JS Ends -----

    //------------- accept connection request Button JS ----------------
    jq('.acceptConnectionRequest').click(
        function() {
            var relationId = this.id.split("acceptConnectionRequest")[0];
            jq.get("connections/connections/acceptConnectionRequest.action", {
                relationshipId: relationId
            }).done(function() {
                location.reload();
            });
        });
    //------------------- accept connection request Button save JS Ends -----

    //------------- ignore connection request Button JS ----------------
    jq('.ignoreConnectionRequest').click(
        function() {
            var relationId = this.id.split("ignoreConnectionRequest")[0];
            jq.get("connections/ignoreConnectionRequest.action", {
                relationshipId: relationId
            }).done(function() {
                location.reload();
            });
        });
    //------------------- ignore connection request Button save JS Ends -----
    //------------------- Messages Page JS ----------------------

    jq('#newMessageComposeDiv').hide();
    jq('#showDetailedList').hide();
    jq('.detailedMessageList').hide();
    jq('#composeMessageButton').click(
        function() {
            jq('#newMessageComposeDiv').show();
            jq('#showDetailedList').hide();
            jq('.detailedMessageList').hide();

            // Bring the compose elements into view if the aren't in the viewport
            jq('body').animate({
                    scrollTop: jq("#sendNewMessageButton").offset().top,
                }, 300
            );
        });
    jq('#broadcastMessageButton').click( function() {
        jq('#composeMessageButton').click();

        jq.get(baseUrl + "/ws/patientportaltoolkit/getallnonvoidedspherepatients",
        function(data) {
            // The returned data is an array of patients
            if(data.length > 0) {
                jq("#sendingto").val("All Active SPHERE Patients: " + data.map(patient => patient.GivenName + ' ' + patient.FamilyName).join(", "));
                jq("#recipientPersonUUID").val(data.map(patient => patient.id).join(","));
            } else {
                jq("#sendingto").val("No patients to broadcast to");
            }
        });
    });

    jq(function() {

        // The following is intended to facilitate updating the screen with the contents
        // after a message reply is sent. After replying to a message, the page is reloaded.
        // To view a specific message, the message has to be clicked on. The following finds
        // the message that was most recently viewed, and clicks on it. The idea is that the
        // most recently viewed message was the one that was replied to before the page got 
        // reloaded.

        const shouldShowLastViewedMessage = localStorage.getItem("shouldShowLastViewedMessage");
        const lastViewedMessageId = localStorage.getItem("lastViewedMessageId");
        if (shouldShowLastViewedMessage === "true" && lastViewedMessageId) {
            jq(`#${lastViewedMessageId}`).click();
        }
        localStorage.removeItem("shouldShowLastViewedMessage");
        localStorage.removeItem("lastViewedMessageId");
    });

    // When a message in the list of messages is clicked
    jq('.messagelistLink').click(function() {
        const messageId = this.id;
        jq(".messagelistLink").css("background", "#FFFFFF");
        jq("#" + this.id).css("background", "#F8F8F8");

        jq('#newMessageComposeDiv').hide();
        jq('#showDetailedList').show();
        jq('.detailedMessageList').hide();

        jq('#mediaList' + this.id).show();
        jq('#sendingReplyMessageSubject' + this.id).val(jq(this).data('messageTitle'));

        // Fire off POST to mark message as read if the reader is the receiver
        // At this level, we don't know for sure if the reader is the receiver, but
        // the server will verify that
        jq.get("composeMessage/markMessageAsRead.action", {
            messageUuid: messageId
        }).done(function() { });

        // Scroll to the message view
        jq('body').animate({
            scrollTop: jq("#mediaList"+ this.id).offset().top,
            }, 300
        );

        // Save this as the last read message so that it automatically opens
        // when the page is reloaded after a message is replied to
        localStorage.setItem("lastViewedMessageId", messageId);
    });
    //------------------- Messages Page JS Ends ----------------------
    jq('.profileBadge').profileBadge();
    jq('.profileBadgeJournals').profileBadge({
        border: {
            width: 0
        },
        margin: 0,
        size: 30
    });
    jq('.profileBadgeHeader').profileBadge({
        border: {
            width: 0
        },
        margin: 0,
        size: 90
    });

    //------------------- Reply message JS ----------------------
    jq('.sendReplyMessageButton').click(
        function() {
            const messageid = this.id.split("sendReplyMessageButton")[1];
            jq("#sendingReplyMessageText" + messageid).prop("disabled", true);

            localStorage.setItem("shouldShowLastViewedMessage", "true");

            jq.get("composeMessage/sendReplyMessage.action", {
                parentUuid: messageid,
                subject: jq("#sendingReplyMessageSubject" + messageid).val(),
                message: jq("#sendingReplyMessageText" + messageid).val(),
            }).done(function() {
                jq("#sendingReplyMessageText" + messageid).prop("disabled", false);
                logEvent('clicked_Messages_Reply', JSON.stringify({
                    'messageId': messageid
                }))
                location.reload();
            });
        });
    //------------------- Reply message JS Ends ----------------------

    //------------------- compose message JS ----------------------

    const inboxContainer = jq("#inboxContainer");
    let messageSenderUUID = null;
    if(inboxContainer.length) {
        messageSenderUUID = inboxContainer.data("sendingPersonUuid");
    }

    var listOfRelationsData = [];
    jq.when(jq.get(baseUrl + "/ws/patientportaltoolkit/getallrelations",
        function(data, status) {
            
            const peopleDetails = {};

            jq.each(data, function(index, v) {
                const patientId = data[index]["patient"]["id"];
                const patientName = data[index]["patient"]["GivenName"] + " " + data[index]["patient"]["FamilyName"];
                const patientIsDoctor = data[index]["relationType"]["bIsToA"] === "Doctor"// It reads a bit hilariously, but please forgive us 🤣
                
                const relatedPersonId = data[index]["relatedPerson"]["id"];
                const relatedPersonName = data[index]["relatedPerson"]["GivenName"] + " " + data[index]["relatedPerson"]["FamilyName"];
                const relatedPersonIsADoctor = data[index]["relationType"]["aIsToB"] === "Doctor";

                if (messageSenderUUID === patientId && !relatedPersonIsADoctor) {                 
                    peopleDetails[relatedPersonId] = {
                        id: relatedPersonId,
                        value: relatedPersonName
                    }
                }
                if(messageSenderUUID === relatedPersonId && !patientIsDoctor) {      
                    peopleDetails[patientId] = {
                        id: patientId,
                        value: patientName,
                    }
                }
            });

            listOfRelationsData = Object.values(peopleDetails);

        })).then(function() {
        const sendingToAutocomplete = jq("#sendingto").autocomplete({
            source: listOfRelationsData,
            minLength: 3,
            select: function(event, ui) {
                event.preventDefault();

                jq("#sendingto").val(ui.item.value);
                jq("#recipientPersonUUID").val(ui.item.id);
            },
        });
        if (sendingToAutocomplete.length) {
            sendingToAutocomplete.data("ui-autocomplete")._renderItem = function(ul, item) {
                
                let value = item.value;
                let listItem;
                
                listItem = jq("<li>")
                .append('<a>' + value + '</a>')
                .appendTo(ul);

                return listItem;                
            };
        }
        const recipientSuggestionsContainer = jq('#recipientSuggestionsContainer');
        if (recipientSuggestionsContainer.length) {

            if (listOfRelationsData.length) {
                recipientSuggestionsContainer.show();

                listOfRelationsData.forEach((relationData, index) => {
                    const button = jq('<button type="button" class="btn btn-default btn-xs recipient-suggestion-button" style="margin:2px" data-recipient-uuid="'
                    + relationData.id +'" data-recipient-name="'+ relationData.value +'">'+ relationData.value +'</button>');
                    button.appendTo(recipientSuggestionsContainer);
                });
                
                jq('.recipient-suggestion-button').click(function (e){                    
                    jq("#sendingto").val(jq(this).data('recipientName'));
                    jq("#recipientPersonUUID").val(jq(this).data('recipientUuid'));
                });
            }
        }
    });

    jq('#sendNewMessageButton').click(
        function() {
            // Disable the message compose
            jq("#newMessageComposeDiv fieldset").prop("disabled", true);
            
            // Send the message
            jq.get("composeMessage/sendNewMessage.action", {
                personUuidStringList: jq("#recipientPersonUUID").val(),
                subject: jq("#sendingNewMessageSubject").val(),
                message: jq("#sendingNewMessageText").val(),
            }).done(function() {
                
                // Log that the message was sent
                logEvent('clicked_ComposeMessage_Send', JSON.stringify({
                    'message': jq('#sendingNewMessageText').val()
                }));

                // Re-enable the message compose
                jq("#newMessageComposeDiv fieldset").prop("disabled", false);
                
                // Reload the page
                location.reload();
            });
        });

    //------------------- compose message JS ENDS ----------------------

    //------------------- Follow up care JS ----------------------

    //JS for the Button Events


    jq('.markScheduledReminder').click(
        function() {
            var reminderID = this.id.split("markScheduledReminder")[1];
            jq('#markScheduledIdHolder').val(reminderID);
        });
    jq('#saveMarkScheduledButton').click(
        function() {
            jq.get("appointments/markScheduled.action", {
                reminderId: jq("#markScheduledIdHolder").val(),
                markScheduledDate: jq("#markScheduledDate").val()
            }).done(function() {
                location.reload();
            });
        });
    //JS for the Button Events END

    //------------------- Follow up care JS ENDS ----------------------

    // make all items having class 'edit' editable
    jq('.edit').editable();

    jq('[data-toggle="tooltip"]').tooltip();


    jq('.mycancerbuddies').click(
        function() {
            jq('#mycancerbuddiesSave').removeClass('disabled');
            jq('#mycancerbuddiesSave').prop('disabled', false);
        });
    //------------------- Function to log events ----------------------

    const uiActivityIndicator = jq("#uiActivityIndicator");
    jq(document)
        .ajaxStart(function () {
            uiActivityIndicator.show();
        })
        .ajaxStop(function () {
            uiActivityIndicator.fadeOut(200);
        })
});



function logEvent(event, data) {
    jq.ajax({
        type: "POST",
        async: false,
        url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/patientportaltoolkit/logEvent",
        data: {
            event: event,
            data: data
        },
        success: function(response) {
            console.log(response);
        },
        error: function(jqXHR, textStatus, errorThrown) {            
            console.error({jqXHR, errorThrown})
        }
    });
}