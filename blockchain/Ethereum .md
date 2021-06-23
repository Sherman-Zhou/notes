# Install
1. geth
2. ganache
3. truffle `npm i truffle -g`

# create private node

1. puppeth
2. init private node:

  ```
  geth --datadir . init private.json

  ```
3. new account `geth --datadir . account new `
 

4. list account `geth --datadir . account list`
5. start the node: 
```
geth --networkid 4224 --mine --minerthreads 1 --datadir "." --nodiscover --rpc --rpcport "8545" --port "30303" --rpccorsdomain "*" --nat "any" --rpcapi eth,web3,personal,net --allow-insecure-unlock --unlock 0 --password ./password.sec

```

6. attach to Geth javascript console

`geth attach ipc:\\.\pipe\geth.ipc`
 
 ```
//cmd in js console
//https://geth.ethereum.org/docs/interface/javascript-console

eth.getBalance(eth.coinbase)
eth.getBalance(eth.accounts[1])
web3.fromWei(eth.getBalance(eth.coinbase), "ether")
miner.stop()
miner.start(1)
 eth.sendTransaction({from:eth.coinbase, to:eth.accounts[1], value: web3.toWei(10,"ether")})
```

# Account and Sign

1. account
 - EOA: External Owned Account
```
 private key -> (ECDSA)-> public key -> Ethereum Accout(last 20b of Kecca Hash of pub key)

```
 - Contract Account
 
2. sign

```
 private key-> sign-> signed transaction


```

# Smart Contract

##   pre-compiler statement
 1. data type
  - uint256/uint8/int8
  - bool
  - address (20byte: start with 0x)
  - string
  - mapping

  ```
  contract SimpleMappingExample {
      mapping(uint256 => bool) myMapping;
      
      function setValue(uint256 _index) public {
          myMapping[_index] = true;
      }
      
  }
  ```
   - struct
   ```
    struct Payment {
      uint256 amount;
      uint256 timestamps;
    }

    Payment memory payment = Payment(msg.value, now)

   ```
  - enum
  - array


 2. Global Objects:
  - msg.sender
  - msg.value
  - now

 3. delete Contract
  ` selfdestruct(_to);`
 4. samples:
```
pragma solidity >=0.7.0 <0.8.0;

contract MyContract {
    string public myString ="Hello World!";
    unit256 public myUnit;
    bool public myBool = true;
    address public receiver;

    function getBalanceOfAddresss  public view return (unit) {
      return receiver.balance;
    }
}

contract SendMoneyExample {
    
    uint256 public balanceReceived;

    address owner;
    
    construtor() public {

      owner = msg.sender;
    }
    function receiveMoney() public payable {
        balanceReceived += msg.value;
        
    }
    
    function getBalance() public view returns (uint256){
        return address(this).balance;
    }
    
    function withdrawMoney () public {
        <!-- address payable to = msg.sender;
        to.transfer(this.getBalance());
    }
     -->
    function withdrawMoneyTo(address payable _to) public {
       require(msg.sender == owner, "You are not the owner")
        _to.transfer(this.getBalance());
    }
}

```
## Exception
  1. require()
  2. assert()

##  Fallback function
```
  function() external payable {
    //...
  }
```
## pure function

```

 function convert(uint256 wei) public pure returns(unit256) {

 }

```

### Modifier

```
 modifer onlyOwner() {
   requre(msg.sender == owner, "you are not allowed")
   _
 }

 function createToken() public onlyOwner {
   totalBalance[owner]++;
 }

```

### Inheritance: multile inheritance
```

//in file base.sol
contract Base {

}

//in sol.sol
import "./base.sol"
contract Son is Base {

}

```
### event

## Remix
 deployment Env:
  -  Injected Web3: MetaMask
  -  Javascript VM: enbeded 
  -  Web3 Provider: local node


