${ ui.includeFragment("patientportaltoolkit", "headerForApp") }
<form id="adminDashCardForm" class="form-horizontal col-xs-8">

        <div class="clearfix">
            <div class="pull-left">
                <h4>Categorize patients</h4>
            </div>
            <br/>
            <br/>
            <br/>
        </div>
        <hr>
        
        <div class="clearfix">
            <div class="pull-left">
                <h4>Categorize forms</h4>
            </div>
            <br/>
            <br/>
            <br/>
        </div>
        <hr>

        <div class="clearfix">
            <div class="pull-left">
                <h4>Categorize/create followup rules </h4>
            </div>
            <br/>
            <br/>
            <br/>
        </div>
        <hr>
        
        <div class="clearfix">
            <div class="pull-left">
                <h4>Categorize/create side effect rules</h4>
            </div>
            <br/>
            <br/>
            <br/>
        </div>
        <hr>
        
        <div class="clearfix">
            <div class="pull-left">
                <h4>Categorize/Create preventive care rules</h4>
            </div>
            <br/>
            <br/>
            <br/>
        </div>
        <hr>
        
        <div class="clearfix">
            <div id="divCategorizeCommunitiesData" class="pull-left">
                    <div>   
                            <div class="clearfix">
                                <h4>Cancer Community Data &emsp;
                                </h4>
                            </div>
                            <br />
                            <div>
                                 <div id="accordion">
                                     <% CancerCommunityData.each { cancerCommunity -> %>
                                        <h3>${(cancerCommunity.cancerType)}</h3> 
                                        <div>
                                            <div class="clearfix">
                                                <label><b>Cancer Type:</b></label>
                                                <label> <span id="cancerType${(cancerCommunity.id)}">${(cancerCommunity.cancerType)}</span> </label>
                                                <div class="pull-right">
                                                    <a id="${(cancerCommunity.id)}" class="no-underline-edit fa fa-pencil fa-lg editCancerCommunityDataresourcesButton"  data-toggle="modal" data-target="#edit-CancerCommunityResourcesData-modal"></a>
                                                 </div>
                                            </div>
              
                                            <div class="clearfix">
                                                <div class="pull-left">
                                                   <div>
                                                        <span id="usefulContacts${(cancerCommunity.id)}">${(cancerCommunity.usefulContacts)}</span>
                                                   </div>
                                                   <div>
                                                      <span id="resources${(cancerCommunity.id)}">${(cancerCommunity.resources)}</span>
                                                   </div>
                                                </div>
                                            </div>
                                       </div>
                                     <% } %>
                                 </div>
                            </div>
                    </div>
            </div>
            ${ ui.includeFragment("patientportaltoolkit", "editCancerCommunityResources")}
        </div>

        <hr />
</form>

<script>
    jq( function() {
        jq("#accordion").accordion();
    });
</script>