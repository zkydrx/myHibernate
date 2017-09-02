package com.tyd.pojo;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Abbot
 * Date: 2017-09-02
 * Time: 16:58
 * Description:
 */
@Entity
@Table(name = "hibernate", schema = "hibernate", catalog = "")
public class HibernateEntity
{

    private int id;
    private String name;
    private String age;

    @Id
    @Column(name = "id", nullable = false)
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Basic
    @Column(name = "age", nullable = true, length = 50)
    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        HibernateEntity that = (HibernateEntity) o;

        if (id != that.id)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (age != null ? !age.equals(that.age) : that.age != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }
}
