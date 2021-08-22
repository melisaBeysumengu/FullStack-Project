import React, { useEffect, useState } from 'react';
import MaterialTable from 'material-table';
import axios from 'axios';
import { Alert, AlertTitle } from '@material-ui/lab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import NotInterestedIcon from '@material-ui/icons/NotInterested';
import { grey } from '@material-ui/core/colors';
//import "../src/styles.css";

// // regex for email validation
// const validateEmail = (email) => {
//     const re = /^((?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\]))$/;
//     return re.test(String(email).toLowerCase());
// }


const Products = () => {

    const [product, setProduct] = useState([]);
    const [iserror, setIserror] = useState(false);
    const [errorMessages, setErrorMessages] = useState([]);

    let columns = [
        { title: 'TITLE', field: 'title' },
        { title: 'PRICE', field: 'price' },
        { title: 'SELLER', field: 'seller.username' },
        { title: 'PICTURE', field: 'pictureUrl' }
    ]

    useEffect(() => {
        axios.get(`http://localhost:8080/api/product/`)
            .then(res => {
                const products = res.data;
                setProduct(products);
                // console.log(users);
            })
    }, [])



    //function for updating the existing row details
    const handleRowUpdate = (newData, oldData, resolve) => {
        //validating the data inputs
        let errorList = []
        if (newData.title === "") {
            errorList.push("Try Again, You didn't enter the title field")
        }
        if (newData.price === "") {
            errorList.push("Try Again, You didn't enter the price field")
        }
        // if (newData.email === "" || validateEmail(newData.email) === false) {
        //     errorList.push("Oops!!! Please enter a valid email")
        // }
        if (newData.seller === "") {
            errorList.push("Seller field can't be blank")
        }

        if (errorList.length < 1) {
            axios.put(`http://localhost:8080/api/product/${newData.id}`, newData)
                .then(response => {
                    const updateUser = [...product];
                    const index = oldData.tableData.id;
                    updateUser[index] = newData;
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
        axios.delete(`http://localhost:8080/api/product/${oldData.id}`)
            .then(response => {
                const dataDelete = [...product];
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
        if (newData.title === "") {
            errorList.push("Try Again, You didn't enter the title field")
        }
        if (newData.price === "") {
            errorList.push("Try Again, You didn't enter the price field")
        }

        if (errorList.length < 1) {
            axios.post(`http://localhost:8080/api/product/`, newData)
                .then(response => {
                    let newUserdata = [...product];
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

    const handleLike = (likeId, resolve, url) => {
        axios.put('http://localhost:8080/api/'+url, likeId, {
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                const updateUser = [...product];
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
    }



    return (
        <div className="app">
            <MaterialTable
                actions={[
                    {
                        icon: () =>  <FavoriteIcon color={"secondary"} />,
                        tooltip: 'Like Product',
                        onClick: (event, rowData) => {
                            new Promise((resolve) => {
                                handleLike(rowData.id, resolve, "favorites/")
                            })
                        },
                        iconProps: { color: "primary" }
                    },
                    {
                        icon: () =>  <NotInterestedIcon style={{ color: grey[900] }} />,
                        tooltip: 'BlackList Seller',
                        onClick: (event, rowData) => {
                            new Promise((resolve) => {
                                handleLike(rowData.seller.username, resolve, "black-list/")
                                console.log(rowData.seller.username)
                            })
                        },
                        iconProps: { color: "primary" }
                    }
                ]}
                title="Product Details"
                columns={columns}
                data={product}
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

export default Products;