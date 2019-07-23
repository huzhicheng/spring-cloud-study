import React from "react";
// import DevTools from "mobx-react-devtools";
import { observer, inject } from "mobx-react";

@inject("leftNavStore")
@observer
class BaseInfoView extends React.Component {
  constructor(props) {
    super(props);
    console.log(this.props.leftNavStore);
  }

  changeUsername = () => {
    let name = "what are you doing";
    this.props.leftNavStore.changeUsername(name);
    console.log("执行changeUserName方法，value=" + name);
  };

  render() {
    console.log("render again");
    console.log(this.props.leftNavStore.username);
    const { username } = this.props.leftNavStore;
    return (
      <div className="App">
        {username}
        <input type="button" onClick={this.changeUsername} value="可以的" />
        {/* <DevTools /> */}
      </div>
    );
  }
}

export default BaseInfoView;
