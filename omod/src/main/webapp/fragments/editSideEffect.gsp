<div class="modal fade modal-wide treatment_form_uniform_label_width"  id="edit-SideEffect-modal" role="dialog" aria-labelledby="editSideEffectData" aria-hidden="true">
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
             <h4 class="modal-title">Side Effect Rules</h4>
             <button type="button" class="close cancelModal" aria-label="Close"> <span aria-hidden="true">&times;</span> </button>
        </div>

        <div class="modal-body">
            <input id="sideEffect_Operation" type="hidden" >
            <br />

            <form class="form-inline" role="form">
                <label class="reformatText">Side Effect Condition Name:</label>
                <input class="form-control" id="sideEffect_ConditionName" type="text" />
            </form>
            <br />

            <form class="form-inline" role="form">
                 <label class="reformatText" id="labelSideEffect_Concepts" >Side Effect Concepts:</label>
                 <input class="form-control" id="sideEffect_Concepts" type="text" />
            </form>
            <br />

            <form class="form-inline" role="form">
                <label class="reformatText">Add New Side Effect Concept</label>
                <input class="form-control searchConcept" id="addNewSideEffect_Concept" type="text" />
            </form>
            <br />
        </div>
                
        <div class="modal-footer">
             <div id="sideEffectErrorDetails" style="color: red" hidden></div>
             <div class="button-div pull-right">
                        <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                        <button type="button" class="btn btn-primary" id="saveSideEffectRules">Save Changes</button>
             </div>
        </div>
    </div>
</div>
