<div class="modal fade modal-wide treatment_form_uniform_label_width"  id="edit-PreventiveCareGuideLine-modal" role="dialog" aria-labelledby="editPreventiveCareGuideLinesData" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Preventive Care GuideLine</h4>
                <button type="button" class="close cancelModal" aria-label="Close"> <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <input id="pcgOperation" type="hidden" >

                <form class="form-inline" role="form">
                    <label class="reformatText">Prevetice Care Concept Id:</label>
                    <input class="form-control searchConcept" id="pcg_ConceptId" type="text" />
                </form>
                <br />

                <form class="form-inline" role="form">
                    <label class="reformatText">Follow Up TimeLine:</label>
                    <input class="form-control" id="pcg_followupTimeLine" type="text" />
                </form>
                <br />

            </div>
                
            <div class="modal-footer">
                 <div id="pcgErrorDetails" style="color: red" hidden></div>
                 <div class="button-div pull-right">
                        <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                        <button type="button" class="btn btn-primary" id="savePreventiveCareGuideLines">Save Changes</button>
                 </div>
            </div>
        </div>
    </div>
</div>
