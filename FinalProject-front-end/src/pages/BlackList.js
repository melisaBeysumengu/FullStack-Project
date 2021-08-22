import React, { useEffect, useState } from 'react';
import MaterialTable from 'material-table';
import axios from 'axios';
import { Alert, AlertTitle } from '@material-ui/lab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import NotInterestedIcon from '@material-ui/icons/NotInterested';
import { grey } from '@material-ui/core/colors';

const BlackList = () => {

    const [user, setProduct] = useState([]);
    const [iserror, setIserror] = useState(false);
    const [errorMessages, setErrorMessages] = useState([]);

    let columns = [
        { title: 'USERNAME', field: 'username' },
        { title: 'E-MAIL', field: 'email' },
        { title: 'ROLES', field: 'roles.name' }
    ]

    useEffect(() => {
        axios.get(`http://localhost:8080/api/black-list/`)
            .then(res => {
                const products = res.data;
                setProduct(products);
                // console.log(users);
            })
    }, [])


    //function for deleting a row
    const handleRowDelete = (oldData, resolve) => {
        axios.delete(`http://localhost:8080/api/black-list/`)
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

    return (
        <div className="app">
            <MaterialTable
                actions={[
                    {
                        icon: () =>  <NotInterestedIcon style={{ color: grey[900] }} />,
                        tooltip: 'BlackListed Seller',
                        iconProps: { color: "primary" }
                    }
                ]}
                title="Black-List Details"
                columns={columns}
                data={user}
                options={{
                    headerStyle: { borderBottomColor: 'red', borderBottomWidth: '3px', fontFamily: 'verdana' },
                    actionsColumnIndex: -1
                }}
                editable={{
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

export default BlackList;