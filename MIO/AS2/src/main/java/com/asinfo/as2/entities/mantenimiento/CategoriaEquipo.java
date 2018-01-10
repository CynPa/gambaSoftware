/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.NotNull;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="categoria_equipo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  17:    */ public class CategoriaEquipo
/*  18:    */   extends EntidadBase
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="categoria_equipo", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_equipo")
/*  25:    */   @Column(name="id_categoria_equipo")
/*  26:    */   private int idCategoriaEquipo;
/*  27:    */   @Column(name="id_organizacion")
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal")
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=20)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=1, max=20)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", nullable=false, length=100)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=100)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", length=200, nullable=true)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   
/*  47:    */   public int getId()
/*  48:    */   {
/*  49: 76 */     return this.idCategoriaEquipo;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdCategoriaEquipo()
/*  53:    */   {
/*  54: 83 */     return this.idCategoriaEquipo;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdCategoriaEquipo(int idCategoriaEquipo)
/*  58:    */   {
/*  59: 91 */     this.idCategoriaEquipo = idCategoriaEquipo;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdOrganizacion()
/*  63:    */   {
/*  64: 98 */     return this.idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdOrganizacion(int idOrganizacion)
/*  68:    */   {
/*  69:106 */     this.idOrganizacion = idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdSucursal()
/*  73:    */   {
/*  74:113 */     return this.idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdSucursal(int idSucursal)
/*  78:    */   {
/*  79:121 */     this.idSucursal = idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getCodigo()
/*  83:    */   {
/*  84:128 */     return this.codigo;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setCodigo(String codigo)
/*  88:    */   {
/*  89:136 */     this.codigo = codigo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getNombre()
/*  93:    */   {
/*  94:143 */     return this.nombre;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setNombre(String nombre)
/*  98:    */   {
/*  99:151 */     this.nombre = nombre;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getDescripcion()
/* 103:    */   {
/* 104:158 */     return this.descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDescripcion(String descripcion)
/* 108:    */   {
/* 109:166 */     this.descripcion = descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isPredeterminado()
/* 113:    */   {
/* 114:173 */     return this.predeterminado;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setPredeterminado(boolean predeterminado)
/* 118:    */   {
/* 119:181 */     this.predeterminado = predeterminado;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isActivo()
/* 123:    */   {
/* 124:188 */     return this.activo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setActivo(boolean activo)
/* 128:    */   {
/* 129:196 */     this.activo = activo;
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.CategoriaEquipo
 * JD-Core Version:    0.7.0.1
 */