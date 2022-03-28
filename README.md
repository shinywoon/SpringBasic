# SpringBasic
SpringBasic

<table class="table">
    <thead>
    <tr>
        <th scope="col">id</th>
        <th scope="col">title</th>
        <th scope="col">body</th>
    </tr>
    </thead>

    <tbody>
    {{#article}}
        <tr>
            <th scope="row">{{id}}</th>
            <td>{{title}}</td>
            <td>{{body}}</td>
        </tr>
    {{/article}}
    </tbody>
</table>
    <a href="/articles/{{article.id}}/edit" class="btn btn-primary">EDIT</a>
    <a href="/articles/{{article.id}}/delete" class="btn btn-danger">Delete</a>
    <a href="/articles">Go to List</a>
