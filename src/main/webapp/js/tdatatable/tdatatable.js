
export default class TDataTable {

    constructor() {

    }

    setFormId(formId) {
        this.formId = formId;
    }

    setRequestUrl(requestUrl) {
        this.requestUrl = requestUrl;
    }

    setDestination(destination) {
        this.destination = destination;
    }
    
    paginate(paginatorId) {
		this.paginatorId = paginatorId;
		this.paginated = true;
	}

    process() {
        let form = document.getElementById(this.formId);
        let formData = new FormData(form);
        
        if(this.paginated == true) {
			let paginator = document.getElementById(this.paginatorId);
			if(paginator == null ){
				formData.append("pageIndex", 1);
				formData.append("pageSize", 10);
			} else {
				let paginatorData = new FormData(paginator);
				formData.append("pageIndex", paginatorData.get("pageIndex"));
				formData.append("pageSize", paginatorData.get("pageSize"));				
			}
		}
        
        let params = new URLSearchParams(formData).toString();
        
        fetch(
            this.requestUrl + "?" + params,
            {
                method: "GET"
            }
        ).then(
            response => {
                if(!response.ok) throw new Error( response.json().errors ? response.json().errors : "Internal Error");
                return response.json();
            }
        ).then(
            data => {
                document.getElementById(this.destination).innerHTML = data.table;
            }
        ).catch(
            error => {
                alert(error);
            }
        );
    }

}