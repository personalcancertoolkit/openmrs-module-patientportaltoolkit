${ui.includeFragment("patientportaltoolkit", "headerForApp")}
${ ui.includeFragment("patientportaltoolkit", "treatmentsSurgeriesModal") }
${ ui.includeFragment("patientportaltoolkit", "treatmentsChemotherapyModal") }
<body>
<div class="container bgcontent col-sm-offset-2">
    <div class="form-group col-md-8">
        <input type="text" id="searchPatients" class="form-control" onkeyup="filterPatients()" placeholder="Search for patients.."
               title="Type in a name">
    </div>
        <table id="allPatientTable" class="table table-hover table-striped">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Age</th>
                <th scope="col">MRN</th>
                <th scope="col">Add Treatments</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</body>
<script>
    jq(document).ready(function () {
        jq.ajax({
            type: "GET",
            dataType: 'json',
            url: window.location.href.split("/patientportaltoolkit")[0] + "/ws/patientportaltoolkit/getallpatients",
            success: function (response) {
                var table = document.getElementById("allPatientTable");
                response.forEach(obj => {
                    var row = table.insertRow(-1);
                    var cell0 = row.insertCell(0);
                    var cell1 = row.insertCell(1);
                    var cell2 = row.insertCell(2);
                    var cell3 = row.insertCell(3);
                    cell0.innerHTML = obj.GivenName + " " + obj.FamilyName;
                    cell1.innerHTML = obj.Age;
                    cell2.innerHTML = obj.MRN;
                    cell3.innerHTML = "<button class='btn btn-primary' id=" + obj.id + "+'addGen'>General History</button> <button class='btn btn-primary' onclick='updateSurgeryPatientUuid(this)'  id=" + obj.id + "addSurg data-toggle='modal' data-target='#edit-surgeries-modal'>Surgery</button> <button class='btn btn-primary' onclick='updateChemotherapyPatientUuid(this)'  id=" + obj.id + "addChemo data-toggle='modal' data-target='#edit-chemotherapies-modal'>Chemotherapy</button> <button class='btn btn-primary'  id=" + obj.id + "+'addRad'>Radiation</button>";

                });
            },
            error: function (e) {
                console.log('Error: ' + e);
            },
        });

    });
    function updateSurgeryPatientUuid(event){
        jq(".edit-surgeries-modal #surgeryPatientUuidHolder").val((event.id).split('addSurg')[0]);
    }
    function updateChemotherapyPatientUuid(event){
        jq(".edit-chemotherapies-modal #chemotherapyPatientUuidHolder").val((event.id).split('addChemo')[0]);
    }

    function filterPatients() {
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("searchPatients");
        filter = input.value.toUpperCase();
        table = document.getElementById("allPatientTable");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[0];
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
</script>