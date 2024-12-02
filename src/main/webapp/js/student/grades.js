
import TDataTable from "./../tdatatable/tdatatable.js";

let datatable = new TDataTable();

datatable.setFormId("gradeFilter");    
datatable.setRequestUrl("/ENT/data/student/grades");
datatable.setDestination("gradesDest");
datatable.paginate("studentsPaginator");

document.getElementById('gradeFilter').addEventListener('input', function() {
    datatable.process();
});

datatable.process();

