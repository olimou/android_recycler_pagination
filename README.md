# Android RecyclerPagination

Extensão do RecyclerView para trabalhar com auto paginação ao chegar ao final de uma lista 

Tipos de paginação suportada:
* paginação com incremento de inteiro
* paginação com token string (em desenvolvimento)

## Requisitos

RecyclerPagination pode ser incluído em qualquer aplicação Android.

RecyclerPagination suporte Android API > 14

## Usando RecyclerPagination em sua aplicação

If you are building with Gradle, simply add the following line to the `dependencies` section of your `build.gradle` file:

No build.gradle do projeto adicionar
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

No build.gradle do módulo
```gradle
compile 'com.github.olimou:android_recycler_pagination:CHECK_VERSION'
```

Em sua aplicação Java

```java
public class ExamplePagination extends RecyclerViewPagination<RecyclerView.ViewHolder, String> {

	public ExamplePagination(int _paginationSize, PaginationListener _paginationListener) {
		super(_paginationSize, _paginationListener);
	}

	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		//Adicionar super ao onBindViewHolder 
		super.onBindViewHolder(holder, position);
		
		// seu código aqui...
	}
}
```

Em sua aplicação Kotlin
```kotlin
class ExamplePagination(_paginationSize: Int, _paginationListener: RecyclerViewPagination.PaginationListener) 
            : RecyclerViewPagination<NotifyRecyclerViewAdapter.NotifyHolder, NotificationsData>(_paginationSize, _paginationListener) {

    override fun onBindViewHolder(holder: NotifyHolder?, position: Int) {
        //Adicionar super ao onBindViewHolder 
        super.onBindViewHolder(holder, position)
        
        // seu código aqui...
    }
}
```