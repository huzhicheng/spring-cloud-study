import { observable, action } from 'mobx';

class TestStore {
    @observable username;
    @observable password;

    constructor() {
        this.username = '';
        this.password = '';
    }

    @action changeUsername = (value) => {
        this.username = value;
    }
    @action changePassword = (value) => {
        this.password = value;
    }
}

const testStore = new TestStore();

export default testStore;
export { TestStore };