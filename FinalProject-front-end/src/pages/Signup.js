import { Form, Input, Button, Select } from "antd";
import { useHistory } from "react-router-dom";
import { useState } from "react";
import AuthService from "../service/AuthService";

const Signup = () => {
    const history = useHistory();
    const [credentials, setCredentials] = useState({});

    const onFinish = async (values) => {
        console.log("Success:", values);
        const response = await AuthService.signup(credentials);
        if (response) {
            history.push("/");
        } else {
            //alert("Something went wrong! Try again.")
        }
    };

    const onFinishFailed = (errorInfo) => {
        console.log("Failed:", errorInfo);
    };

    const { Option } = Select;

    const handleChange = (event) => {
        setCredentials({
            ...credentials,
            [event.target.name]: event.target.value
        });
    };

    return (
        <Form
            name="basic"
            labelCol={{
                span: 8
            }}
            wrapperCol={{
                span: 16
            }}
            initialValues={{
                remember: true
            }}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            style={{ margin: "0 auto", width: 400 }}
        >
            <Form.Item
                label="Username"
                name="username"
                rules={[
                    {
                        required: true,
                        message: "Please input your username!"
                    }
                ]}
            >
                <Input
                    onChange={handleChange}
                    name="username"
                    value={credentials.username}
                />
            </Form.Item>

            <Form.Item
                label="E-mail"
                name="email"
                rules={[
                    {
                        required: true,
                        message: "Please input your e-mail!"
                    }
                ]}
            >
                <Input
                    onChange={handleChange}
                    name="email"
                    value={credentials.email}
                />
            </Form.Item>

            <Form.Item
                label="Role"
                name="roles"
                rules={[
                    {
                        required: true,
                    },
                ]}>
                <Select
                    placeholder="Select a role"
                    onChange={(value) => {
                        setCredentials({
                            ...credentials,
                            ["roles"]: [{["name"]:value}]
                        });
                    }

                    }
                    allowClear
                >
                    <Option name="roles" value="ROLE_USER">User</Option>
                    <Option name="roles" value="ROLE_SELLER">Seller</Option>
                    <Option name="roles" value="ROLE_ADMIN">Admin</Option>
                </Select>
            </Form.Item>

            <Form.Item
                label="Password"
                name="password"
                rules={[
                    {
                        required: true,
                        message: "Please input your password!"
                    }
                ]}
            >
                <Input.Password
                    onChange={handleChange}
                    name="password"
                    value={credentials.password}
                />
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 8,
                    span: 16
                }}
            >
                <Button type="primary" htmlType="submit">
                    Submit
                </Button>
            </Form.Item>

        </Form>
    );
};

export default Signup;