<div class="modal fade modal-wide treatment_form_uniform_label_width"  id="edit-GuideLine-modal" role="dialog" aria-labelledby="editGuideLinesData" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Follow up Rules</h4>
                <button type="button" class="close cancelModal" aria-label="Close"> <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <input id="guideLineOperation" type="hidden" >

                <form class="form-inline" role="form">
                    <input type="checkbox" name="checkboxconditionset" id="coloncancerstage1" value="coloncancerstage1" />Colon cancer, Stage 1<br />
                    <input type="checkbox" name="checkboxconditionset" id="coloncancerstage2" value="coloncancerstage2" />Colon cancer, Stage 2<br />
                    <input type="checkbox" name="checkboxconditionset" id="coloncancerstage3" value="coloncancerstage3" />Colon cancer, Stage 3<br />
                    <input type="checkbox" name="checkboxconditionset" id="rectalcancerstage1" value="rectalcancerstage1" />Rectal cancer, Stage 1<br />
                    <input type="checkbox" name="checkboxconditionset" id="rectalcancerstage2" value="rectalcancerstage2" />Rectal cancer, Stage 2<br />
                    <input type="checkbox" name="checkboxconditionset" id="rectalcancerstage3" value="rectalcancerstage3" />Rectal cancer, Stage 3<br />
                </form>
                <br />

                <form class="form-inline" role="form">
                    <label class="reformatText">Guideline ConceptID</label>
                    <input class="form-control" id="guidLine_ConceptId" type="text" />
                </form>
                <br />

                <form class="form-inline" role="form">
                    <label class="reformatText">Guideline Name</label>
                    <input class="form-control" id="guidLine_Name" type="text" />
                </form>
                <br />

                <form class="form-inline" role="form">
                    <label class="reformatText">Followup TimeLine</label>
                    <input class="form-control" id="guideLine_FollowupTimeLine" type="text" />
                </form>
                <br />

            </div>
                
            <div class="modal-footer">
                 <div id="guideLineErrorDetails" style="color: red" hidden></div>
                 <div class="button-div pull-right">
                       <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                       <button type="button" class="btn btn-primary" id="saveGuideLines">Save Changes</button>
                 </div>
            </div>
        </div>
    </div>
</div>


<script>
    jq( function() {
        
        jq("#accordion").accordion();
        
    });
</script>