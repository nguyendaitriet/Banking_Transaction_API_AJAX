class App {

    static SweetAlert = class {
        static showSuspendConfirmDialog() {
            return Swal.fire({
                icon: 'warning',
                text: 'Are you sure to suspend the selected customer ?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, please suspend this client !',
                cancelButtonText: 'Cancel',
            })
        }

        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 2500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }
    }

    static IziToast = class {
        static showSuccessAlert(t) {
            iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 2500,
                message: t
            });
        }

        static showErrorAlert(t) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 3500,
                message: t
            });
        }
    }
}


class Customer {
    constructor(id, fullName, email, phone, address, balance, deleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.deleted = deleted;
    }
}

class Sender extends Customer {
    constructor() {
        super();
    }
}

class Recipient extends Customer {
    constructor() {
        super();
    }
}

class Deposit {
    constructor(id, transactionAmount, createdAt, createdBy, updatedAt, updatedBy, deleted, customerId) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.customerId = customerId;
    }
}

class Withdraw {
    constructor(id, transactionAmount, createdAt, createdBy, updatedAt, updatedBy, deleted, customerId) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.customerId = customerId;
    }
}

class Transfer {
    constructor(id, transactionAmount, fees, feesAmount, transferAmount, senderId, recipientId, createdAt, createdBy, updatedAt, updatedBy, deleted, customerId) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.transferAmount = transferAmount;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }
}