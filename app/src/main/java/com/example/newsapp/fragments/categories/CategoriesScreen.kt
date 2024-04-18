package com.example.newsapp.fragments.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapp.model.Constants
import com.example.newsapp.model.NewsScreen
import com.example.newsapp.ui.theme.gray4

@Composable
fun CategoriesScreen(vm: CategoriesViewModel= viewModel(), navController: NavHostController) {
    Column {
        Text(
            text = stringResource(R.string.pick_your_category_of_interest),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = gray4,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        CategoriesList(vm,navController)

    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesFragmentsPreview() {
    CategoriesScreen(viewModel(),rememberNavController())
}

@Composable
fun CategoriesList(vm: CategoriesViewModel,navController: NavHostController) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(vm.categories.size) { position ->
            CategoryCard(
                category = vm.categories.get(position),
                position, navController = navController
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoriesListPreview() {
    CategoriesList(viewModel(),rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: Category, index: Int, navController: NavHostController) {

    Card(
        shape = if (index % 2 == 0)
            RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp, bottomStart = 25.dp)
        else
            RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp, bottomEnd = 25.dp),
        colors =
        CardDefaults.cardColors(containerColor = colorResource(id = category.backGroundColor)),
        modifier = Modifier
            .padding(8.dp)
            .height(120.dp),
        onClick = {
             navController.navigate(route = "${NewsScreen().route}/${category.apiId}")
        }
    ) {
        Image(
            painter = painterResource(id = category.drawableResId),
            contentDescription = "Category card image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxHeight(0.70f),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = category.categoryNameId),
            color = Color.White,
            fontSize = 22.sp, fontWeight = FontWeight.Normal,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    CategoryCard(
        category = Constants.categories.get(0), 0,
        rememberNavController()
    )
}
