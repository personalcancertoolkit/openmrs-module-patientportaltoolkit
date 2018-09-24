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
                      <label class="reformatText">Cancer Type</label> 
                       <select class="form-control" id="dropDownPreventiveCancerType">
                               <option class="reformatText" value="thyroidcancer1">Thyroid Cancer</option>
                               <option class="reformatText" value="colorectalcancer2">Colorectal Cancer</option>
                               <option class="reformatText" value="coloncancer3">Colon Cancer</option>
                       </select>
                </form>
                <br />

                <form class="form-inline" role="form">
                    <label class="reformatText">Prevetice Care Guideline Name</label>
                    <input class="form-control" id="pcg_Name" type="text" />
                </form>
                <br />

                <form class="form-inline" role="form">
                    <label class="reformatText">No. of intervals</label>
                    <input class="form-control" id="pcg_noofIntervals" type="text" />
                </form>
                <br />

                <form class="form-inline" role="form">
                    <label class="reformatText">Interval Length</label>
                    <input class="form-control" id="pcg_intervalLength" type="text" />
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
