import React, { useEffect, useState } from 'react';
import MaterialTable from 'material-table';
import axios from 'axios';
import { Alert, AlertTitle } from '@material-ui/lab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import NotInterestedIcon from '@material-ui/icons/NotInterested';
import { grey } from '@material-ui/core/colors';

const Users = () => {

    const [user, setProduct] = useState([]);
    const [iserror, setIserror] = useState(false);
    const [errorMessages, setErrorMessages] = useState([]);

    let columns = [
        { username: 'USERNAME', field: 'username' },
        { username: 'E-MAIL', field: 'email' },
        { username: 'ROLES', field: 'roles[0].name' }
    ]

    useEffect(() => {
        axios.get(`http://localhost:8080/api/users/`)
            .then(res => {
                const users = res.data;
                setProduct(users);
                // console.log(users);
            })
    }, [])



    //function for updating the existing row details
    const handleRowUpdate = (newData, oldData, resolve) => {
        //validating the data inputs
        let errorList = []
        if (newData.username === "") {
            errorList.push("Try Again, You didn't enter the username field")
        }
        if (newData.email === "") {
            errorList.push("Try Again, You didn't enter the email field")
        }
        // if (newData.email === "" || validateEmail(newData.email) === false) {
        //     errorList.push("Oops!!! Please enter a valid email")
        // }

        if (errorList.length < 1) {
            axios.put(`http://localhost:8080/api/users/${oldData.username}`, newData)
                .then(response => {
                    const updateUser = [...user];
                    const index = oldData.tableData.id;
                    updateUser[index] = newData;
                    updateUser[index].password="asdfgasd"
                    setProduct([...updateUser]);
                    resolve()
                    setIserror(false)
                    setErrorMessages([])
                })
                .catch(error => {
                    setErrorMessages(["Update failed! Server error"])
                    setIserror(true)
                    resolve()

                })
        } else {
            setErrorMessages(errorList)
            setIserror(true)
            resolve()

        }
    }


    //function for deleting a row
    const handleRowDelete = (oldData, resolve) => {
        axios.delete(`http://localhost:8080/api/user/${oldData.id}`)
            .then(response => {
                const dataDelete = [...user];
                const index = oldData.tableData.id;
                dataDelete.splice(index, 1);
                setProduct([...dataDelete]);
                resolve()
            })
            .catch(error => {
                setErrorMessages(["Delete failed! Server error"])
                setIserror(true)
                resolve()
            })
    }


    //function for adding a new row to the table
    const handleRowAdd = (newData, resolve) => {
        //validating the data inputs
        let errorList = []
        if (newData.username === "") {
            errorList.push("Try Again, You didn't enter the username field")
        }
        if (newData.email === "") {
            errorList.push("Try Again, You didn't enter the email field")
        }

        if (errorList.length < 1) {
            axios.post(`http://localhost:8080/api/user/`, newData)
                .then(response => {
                    let newUserdata = [...user];
                    newUserdata.push(newData);
                    setProduct(newUserdata);
                    resolve()
                    setErrorMessages([])
                    setIserror(false)
                })
                .catch(error => {
                    setErrorMessages(["Cannot add data. Server error!"])
                    setIserror(true)
                    resolve()
                })
        } else {
            setErrorMessages(errorList)
            setIserror(true)
            resolve()
        }
    }

    return (
        <div className="app">
            <MaterialTable
                username="User Details"
                columns={columns}
                data={user}
                options={{
                    headerStyle: { borderBottomColor: 'red', borderBottomWidth: '3px', fontFamily: 'verdana' },
                    actionsColumnIndex: -1
                }}
                editable={{
                    onRowUpdate: (newData, oldData) =>
                        new Promise((resolve) => {
                            handleRowUpdate(newData, oldData, resolve);
                        }),
                    onRowAdd: (newData) =>
                        new Promise((resolve) => {
                            handleRowAdd(newData, resolve)
                        }),
                    onRowDelete: (oldData) =>
                        new Promise((resolve) => {
                            handleRowDelete(oldData, resolve)
                        }),
                }}
            />

            <div>
                {iserror &&
                    <Alert severity="error">
                        <AlertTitle>ERROR</AlertTitle>
                        {errorMessages.map((msg, i) => {
                            return <div key={i}>{msg}</div>
                        })}
                    </Alert>
                }
            </div>

        </div>
    );
}

export default Users;