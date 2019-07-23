import { observable, action } from 'mobx';

class LoginStore {
    @observable username;
    @observable password;

    constructor() {
        this.username = '';
        this.password = '';
    }

    @action changeUsername = (value) => {
        this.username = value;
        console.log("LoginStore:username= " + this.username);
    }
    @action changePassword = (value) => {
        this.password = value;
    }
}

const loginStore = new LoginStore();

export default loginStore;
export { LoginStore };