module.exports = {
    networks: {
        development: {
            host: "localhost",
            port: 8545,
            network_id: "*" // Match any network id
        },
        ropsten:  {
            network_id: 3,
            host: "localhost",
            port:  8545,
            gas:   2900000,
            from: "0x4fDe9FA9AFae70C0d90b0d43C4d912760C08eB47"
        }
    },
    rpc: {
       host: 'localhost',
       post: 8080
   }
};

// GETH - ROPSTEN -
// Address: {39640504c48b318e140a2e6f462e42a9c6143f5c}
// Password: {mcadam2012}
