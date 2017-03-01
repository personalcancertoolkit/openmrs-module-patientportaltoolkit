<script>
    jq(document).ready(function(){
        jq(".preventiveModal").hide();

        var influenzadatePicker= jq( "#influenzaDate" ).datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function() {
            influenzadatePicker.hide();
        }).data('datepicker');
        var pneumococcaldatePicker= jq( "#pneumococcalDate" ).datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function() {
            pneumococcaldatePicker.hide();
        }).data('datepicker');
        var cholesteroldatePicker= jq( "#cholesterolDate" ).datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function() {
            cholesteroldatePicker.hide();
        }).data('datepicker');
        var bpdatePicker= jq( "#bpDate" ).datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function() {
            bpdatePicker.hide();
        }).data('datepicker');
        var hivdatePicker= jq( "#hivDate" ).datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function() {
            hivdatePicker.hide();
        }).data('datepicker');
        var mammographydatePicker= jq( "#mammographyDate" ).datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function() {
            mammographydatePicker.hide();
        }).data('datepicker');
        var cervicaldatePicker= jq( "#cervicalDate" ).datepicker({
            format: 'mm/dd/yyyy'
        }).on('changeDate', function() {
            cervicaldatePicker.hide();
        }).data('datepicker');

        jq("#saveMarkPreventiveCompletedButton").click(
            function () {

                jq.get("preventiveCareModal/saveInfluenzaForm.action", {
                    influenzaDate: jq("#influenzaDate").val()
                }, function () {
                });
                location.reload();
            });

    });
</script>
<div class="modal fade modal-wide"  id="preventiveCare-modal" role="dialog" aria-labelledby="preventiveCareLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close cancelModal" aria-label="Close"><span>&times;</span></button>
                <h4 class="modal-title" id="preventiveCareLabel">Preventive Care</h4>
            </div>
            <div class="modal-body">
                <input id="preventiveCareTypeUuid" value="" type="hidden">
            <% if (influenzaConcepts) { %>
                <div id="influenza-modal" class="preventiveModal">
                <label>Mark Influenza Vaccine Completed</label>
                <br/>
                <br/>
            <% influenzaConcepts.concepts.each { question -> %>
            <% /* influenza date*/ %>
            <% if (question.uuid=="f1cba252-751f-470b-871b-2399565af396") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="influenzaDate" type="text"/>
                </form>
                <% } %>
                <% } %>
                </div>
                <% } %>
                <% if (pneumococcalConcepts) { %>
                <div id="pneumococcal-modal" class="preventiveModal">
                <label>Mark Pneumococcal Vaccine Completed</label>
                <br/>
                <br/>
                <% pneumococcalConcepts.concepts.each { question -> %>
                <% /* pneumococcal date*/ %>
                <% if (question.uuid=="c93df44f-d5b7-49a6-8539-e8265c03dbb3") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="pneumococcalDate" type="text"/>
                </form>
                <% } %>
                <% } %>
            </div>
                <% } %>
                <% if (cholesterolConcepts) { %>
                <div id="cholesterol-modal" class="preventiveModal">
                <label>Mark Cholesterol Screening Completed</label>
                <br/>
                <br/>
                <% cholesterolConcepts.concepts.each { question -> %>
                <% /* cholesterol LDL*/ %>
                <% if (question.uuid=="b0a44f7a-4188-44b3-b86f-955a32d8f4cd") { %>
                <form class="form-inline" role="form"> <label>${(question.getName())} </label>
                    <input class="form-control" id="cholesterolLDLNumber" type="number"/>
                </form>
                <% } %>
                <% if (question.uuid=="4788cb2c-6324-412f-b617-31ef341e7455") { %>
                <form class="form-inline" role="form"> <label>${(question.getName())} </label>
                    <input class="form-control" id="cholesterolTotalNumber" type="number"/>
                </form>
                <% } %>
                <% if (question.uuid=="01f5d7c7-f0c5-4329-8b2d-2053155a962f") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="cholesterolDate" type="text"/>
                </form>
                <% } %>
                <% } %>
            </div>
                <% } %>
                <% if (bpConcepts) { %>
                <div id="bp-modal" class="preventiveModal">
                <label>Mark Blood Pressure Screening Completed</label>
                <br/>
                <br/>
                <% bpConcepts.concepts.each { question -> %>
                <% /* BP Top number*/ %>
                <% if (question.uuid=="63ee5099-567e-4b55-936c-c4c8d71d1144") { %>
                <form class="form-inline" role="form"> <label>${(question.getName())} </label>
                    <input class="form-control" id="bpTopNumber" type="number"/>
                </form>
                <% } %>
                <% /* BP bottom number*/ %>
                <% if (question.uuid=="02310664-f7bb-477c-a703-0325af4c3f46") { %>
                <form class="form-inline" role="form"> <label>${(question.getName())} </label>
                    <input class="form-control" id="bpBottomNumber" type="number"/>
                </form>
                <% } %>
                <% if (question.uuid=="bec04eab-2be5-4f9e-a017-873e3a0b32ab") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="bpDate" type="text"/>
                </form>
                <% } %>
                <br/>
                <% } %>
            </div>
                <% } %>
                <% if (hivConcepts) { %>
                <div id="hiv-modal" class="preventiveModal">
                <label>Mark HIV Screening Completed</label>
                <br/>
                <br/>
                <% hivConcepts.concepts.each { question -> %>
                <% if (question.uuid=="785fd684-c6ca-48d7-9f71-07ae9b5e93d2") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                <br>
                <div class="radio">
                    <label><input type="radio" name="hivradio" value="true"> <span class="reformatText">Positive</span></label>
                    <br/>
                    <label><input type="radio"  name="hivradio" value="false"> <span class="reformatText">Negative</span></label>
                </div>
                </form>
                <% } %>
                <% if (question.uuid=="695ccb4a-a01f-4039-9e00-8f2679b63065") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="hivDate" type="text"/>
                </form>
                <% } %>
                <% } %>
                <br/>
            </div>
                <% } %>
                <% if (mammographyConcepts) { %>
                    <div id="mammography-modal" class="preventiveModal">
                <label>Mark Mammography Screening Completed</label>
                <br/>
                <br/>
                <% mammographyConcepts.concepts.each { question -> %>
                <% if (question.uuid=="39ca0f60-ffe3-49cc-9dcf-7cce8f69c0f5") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <br>
                    <div class="radio">
                        <label><input type="radio" name="mammographyradio" value="true"> <span class="reformatText">Positive</span></label>
                        <br/>
                        <label><input type="radio"  name="mammographyradio" value="false"> <span class="reformatText">Negative</span></label>
                    </div>
                </form>
                <% } %>
                <% if (question.uuid=="d32ef213-d270-4682-bf3a-b81d40b1d661") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="mammographyDate" type="text"/>
                </form>
                <% } %>
                <% if (question.uuid=="c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="mammographyDoctorName" type="text"/>
                </form>
                <% } %>
                <br/>
                <% } %>
                </div>
                <% } %>
                <% if (cervicalConcepts) { %>
                    <div id="cervical-modal" class="preventiveModal">
                <label>Mark Cervical Cancer Screening Completed</label>
                <br/>
                <br/>
                <% cervicalConcepts.concepts.each { question -> %>
                <% if (question.uuid=="838800a3-9991-4fd8-9df1-d6c4f9c2ffae") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <br>
                    <div class="radio">
                        <label><input type="radio" name="cervicalradio" value="true"> <span class="reformatText">Positive</span></label>
                        <br/>
                        <label><input type="radio"  name="cervicalradio" value="false"> <span class="reformatText">Negative</span></label>
                    </div>
                </form>
                <% } %>
                <% if (question.uuid=="baf0de5b-17e7-47c5-a8f5-87d3df4966b4") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="cervicalDate" type="text"/>
                </form>
                <% } %>
                <% if (question.uuid=="c2cb2220-c07d-47c6-a4df-e5918aac3fc2") { %>
                <form class="form-inline" role="form"> <label class="reformatText">${(question.getName())} </label>
                    <input class="form-control" id="cervicalDoctorName" type="text"/>
                </form>
                <br/>
                <% } %>
                <% } %>
                </div>
                <% } %>
            </div>
            <div class="modal-footer">
                <div class="button-div pull-right">
                    <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                    <button type="button" class="btn btn-primary" id="saveMarkPreventiveCompletedButton">Save Changes</button>
                </div>
            </div>
        </div>
    </div>
</div>