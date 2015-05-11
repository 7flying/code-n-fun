
- Compile: ```rustc main.rs```
- Cargo setup
  1. Make a ```Cargo.toml``` file at the top of the directory with:
	 
    ```
    [package]
  
    name = "the_package_name"
    version = "0.0.1"
    authors = ["Some name some@email.com"]
    ```
  2. Run ```cargo build```  to build your project.
  3. Run ```cargo run``` to run your executable or go to the executable
    and run it, should be under the ```target/debug/``` folder.
- Make a new project: ```cargo new project_name --bin```
- Make a new library: ```cargo new lib_name```
