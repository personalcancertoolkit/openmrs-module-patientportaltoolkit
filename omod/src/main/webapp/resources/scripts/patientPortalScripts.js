/**
 * Created by Maurya on 22/06/2015.
 */
jq = jQuery;
jq(document).ready(function(){
    jq(".gen-history-date").val(jq.datepicker.formatDate('mm/dd/yy', new Date(jq(".gen-history-date").val())));
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
    //jq("#searchPersonsInput").keydown(
    //    function () {
    //        if(jq("#searchPersonsInput").val().length>2)
    //        jq.get("searchPersons/getPersonsFromSearch.action", {searchQuery:  jq("#searchPersonsInput").val()}, function(data){
    //            alert(data);
    //        });
    //    });

    jq("#addrelationshipbutton").click(
        function () {
          jq.get("addRelationship/addRelationshipfromForm.action", {given:  jq("#givenpersonName").val(),family:  jq("#familypersonName").val(),gender:jq("#genderSelect").val(),personEmail:jq("#personEmail").val(),personRelationType:jq("#relationshipSelectaddRelation").val()}, function(){
            });
            setTimeout(function(){
                location.reload();
            }, 2000);
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
    //$('#edit-genHistory-modal').on('hidden.bs.modal', function () {
    //    location.reload();
    //})
    $('#saveGeneralHistorybutton').click(
        function () {

            location.reload();
        });
});