import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "antd/dist/antd.css";
import "../src/styles.css";
import { Layout, Menu, Breadcrumb } from "antd";
import Home from "./pages/Home";
import Products from "./pages/Products";
import Users from "./pages/Users";
import FavoriteList from "./pages/FavoriteList";
import BlackList from "./pages/BlackList";
import Signup from "./pages/Signup";

const { Header, Content, Footer } = Layout;

export default function App() {
  return (
    <main>
      <Navbar></Navbar>
      <br /><br /><br /><br /><br /><br />
      <Switch>
        <Route path="/" component={Home} exact />
        <Route path="/products" component={Products} />
        <Route path="/users" component={Users} />
        <Route path="/favorite-list" component={FavoriteList} />
        <Route path="/black-list" component={BlackList} />
        <Route path="/welcome" component={Welcome} />
        <Route path="/sign-up" component={Signup} />
        <Route component={Error} />
      </Switch>
    </main>
  );
}
function Navbar() {
  return (
    <Layout>
      <Header style={{ position: "fixed", zIndex: 1, width: "100%" }}>
        <div className="logo" />
        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={["1"]}>
          <Menu.Item key="1">
            {" "}
            <Link to="/">Home</Link>
          </Menu.Item>
          <Menu.Item key="2">
            {" "}
            <Link to="/products">Products</Link>
          </Menu.Item>
          <Menu.Item key="3">
            {" "}
            <Link to="/users">Users</Link>
          </Menu.Item>
          <Menu.Item key="4">
            {" "}
            <Link to="/favorite-list">Favorite List</Link>
          </Menu.Item>
          <Menu.Item key="5">
            {" "}
            <Link to="/black-list">Black List</Link>
          </Menu.Item>
          <Menu.Item key="6">
            {" "}
            <Link to="/sign-up">SignUp</Link>
          </Menu.Item>
        </Menu>
      </Header>
    </Layout>
    // <div>
    //   <Link to="/">Home </Link>
    //   <Link to="/about">About Us </Link>
    //   <Link to="/products">Products </Link>
    // </div>
  );
};
function Welcome() {
  return (<div>
        <h1>WELCOME</h1>
      </div>)
}

function Error() {
  return <h1>Oops! Page not found!</h1>;
}
/*{ <Router>
      <Layout style={{ height: "100vh" }}>
        <Header style={{ position: "fixed", zIndex: 1, width: "100%" }}>
          <div className="logo" />
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={["1"]}>
            <Menu.Item key="1">
              {" "}
              <Link to="/">Home</Link>
            </Menu.Item>
            <Menu.Item key="2">
              {" "}
              <Link to="/users">Products</Link>
            </Menu.Item>
            <Menu.Item key="3">
              {" "}
              <Link to="/about">About</Link>
            </Menu.Item>
            <Menu.Item key="4">
              {" "}
              <Link to="/crud-table">Products</Link>
            </Menu.Item>
          </Menu>
        </Header>
        <Content
          className="site-layout"
          style={{ padding: "0 50px", marginTop: 64 }}
        >
          {/* <Breadcrumb style={{ margin: "16px 0" }}>
            <Breadcrumb.Item>Home</Breadcrumb.Item>
            <Breadcrumb.Item>Products</Breadcrumb.Item>
            <Breadcrumb.Item>About</Breadcrumb.Item>
            <Breadcrumb.Item>Products</Breadcrumb.Item>
          </Breadcrumb> }
<div
  className="site-layout-background"
  style={{ padding: 24, minHeight: 380 }}
>
  <Switch>
    <Route path="/about">
      <About />
    </Route>
    <Route path="/products">
      <Products />
    </Route>
    <Route path="/">
      <Home />
    </Route>
    <Route path="/crud-table">
      <Products />
    </Route>
  </Switch>
</div>
        </Content >
  <Footer style={{ textAlign: "center" }}>
    Ant Design ©2018 Created by Ant UED
  </Footer>
      </Layout >
    </Router > * /}*/