import axios from "axios";
import LocalStorageUtil from "../util/LocalStorageUtil";

const AuthService = (function () {
  const _signin = async (credentials) => {
    let token = null;

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signin",
        credentials
      );
      if (response && response.data) {
        token = response.data.token;
        LocalStorageUtil.setToken(token);
      }
    } catch (error) {
      console.log(error);
    }

    return token;
  };

  const _signup = async (credentials) => {
    console.log(credentials)
  
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/signup",
        credentials
      );
      if (response && response.data) {
        alert("Welcome {}",response.username)
        return true;
        // token = response.data.token;
        // LocalStorageUtil.setToken(token);
      }
    } catch (error) {
      console.log(error);
    }


  };

  return {
    signin: _signin,
    signup: _signup
  };
})();

export default AuthService;
