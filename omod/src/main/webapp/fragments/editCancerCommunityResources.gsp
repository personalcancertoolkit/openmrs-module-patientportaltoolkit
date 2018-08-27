<div class="modal fade modal-wide treatment_form_uniform_label_width"  id="edit-CancerCommunityResourcesData-modal" role="dialog" aria-labelledby="editCancerCommunityResourcesData" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
                            <div class="modal-header">
                                 <h4 class="modal-title" id="editCancerCommunityResourcesData">Cancer Community Data</h4>
                                <button type="button" class="close cancelModal" aria-label="Close"> <span aria-hidden="true">&times;</span>
                                </button>
                             </div>
                            <div class="modal-body">
                                    <input id="cancerId" type="hidden" >
                                    <form class="form-inline" role="form">
                                          <label class="reformatText">Cancer Type</label> 
                                          <select class="form-control" id="dropdownCancer">
                                              <option class="reformatText" value="thyroidcancer">Thyroid Cancer</option>
                                              <option class="reformatText" value="colorectalcancer">Colorectal Cancer</option>
                                              <option class="reformatText" value="coloncancer">Colon Cancer</option>
                                          </select>
                                    </form>
                                    <br />
                                    <form class="form-inline" role="form">
                                            <label class="reformatText">Useful Contacts</label> <br />
                                           <textarea class="form-control" style="width:1090px;height:360px;" id="txtUsefulContacts"></textarea>
                                    </form>
                                    <br />
                                    <form class="form-inline" role="form">
                                            <label class="reformatText">Resources</label> <br />
                                            <textarea class="form-control" style="width:1090px;height:360px;" id="txtResources"></textarea>
                                    </form>
                            </div>
            
                            <div class="modal-footer">
                                <div class="button-div pull-right">
                                        <div id="cancerCommunityDataErrorDetails" style="color: red" hidden></div>
                                        <button type="button" class="btn btn-default cancelModal">Cancel Changes</button>
                                        <button type="button" class="btn btn-primary" id="saveCategoryCommunityButton">Save Changes</button>
                                </div>
                            </div>
        </div>
    </div>
</div>


<script>

    jq('#saveCategoryCommunityButton').click(
            function () {

                    var CancerId = jq('#cancerId').val();
                    var CancerType = jq('#dropdownCancer :selected').text();
                    var UsefulContacts = jq("#txtUsefulContacts").val();
                    var Resources = jq("#txtResources").val();

                    if(CancerType  == '' || UsefulContacts == '' || Resources == ''){
                        jq('#cancerCommunityDataErrorDetails').text("All Fields are Mandatory");
                        jq('#cancerCommunityDataErrorDetails').show();
                    }
                    else
                    {
                        jq.get("editCancerCommunityResources/saveCancerCommunityResourcesData.action", {
                            cancerId: CancerId,
                            cancerType: CancerType,
                            usefulContacts: UsefulContacts,
                            resources: Resources
                        });
                        setTimeout(
                                function () {
                                    location.reload();
                                }, 2000);
                    }
            });
</script>