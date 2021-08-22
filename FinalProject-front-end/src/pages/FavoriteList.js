import React, { useEffect, useState } from 'react';
import MaterialTable from 'material-table';
import axios from 'axios';
import { Alert, AlertTitle } from '@material-ui/lab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import NotInterestedIcon from '@material-ui/icons/NotInterested';
import { grey } from '@material-ui/core/colors';

const FavoriteList = () => {

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
        axios.get('http://localhost:8080/api/favorites/')
            .then(res => {
                const products = res.data;
                setProduct(products);
                // console.log(users);
            })
    }, [])

    //function for deleting a row
    const handleRowDelete = (oldData, resolve) => {
        axios.delete(`http://localhost:8080/api/favorites/`, oldData.id, {
            headers: {
                "Content-Type": "application/json"
            }
        })
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


    const handleLike = (likeId, resolve, url) => {
        axios.put('http://localhost:8080/api/' + url, likeId, {
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
                        icon: () => <FavoriteIcon color={"secondary"} />,
                        tooltip: 'Like Product',
                        onClick: (event, rowData) => {
                            new Promise((resolve) => {
                                handleLike(rowData.id, resolve, "favorites/")
                            })
                        },
                        iconProps: { color: "primary" }
                    },
                    {
                        icon: () => <NotInterestedIcon style={{ color: grey[900] }} />,
                        tooltip: 'BlackList Seller',
                        onClick: (event, rowData) => {
                            new Promise((resolve) => {
                                handleLike(rowData.seller.username, resolve, "black-list/")
                            })
                        },
                        iconProps: { color: "primary" }
                    }
                ]}
                title="Favorite-List Details"
                columns={columns}
                data={product}
                editable={{
                    onRowDelete: (oldData) =>
                        new Promise((resolve) => {
                            handleRowDelete(oldData, resolve)
                            console.log(oldData.id)
                        })
                }}
                options={{
                    headerStyle: { borderBottomColor: 'red', borderBottomWidth: '3px', fontFamily: 'verdana' },
                    actionsColumnIndex: -1
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

export default FavoriteList;