import { observable, action } from 'mobx';

class LeftNavStore {
    @observable isFold;

    @observable username;

    @observable userid;

    
    constructor() {
        this.isFold = false;
        this.username = 'hello';
        this.userid = '';
    }

    @action toogleFold = (isFold) => {
        this.isFold = isFold;
    }

    @action changeUsername = (name) =>{
        this.username = name;
    }
    
}

const leftNavStore = new LeftNavStore();

export default leftNavStore;
export { LeftNavStore };