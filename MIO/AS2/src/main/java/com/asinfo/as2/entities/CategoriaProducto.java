/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="categoria_producto")
/*  15:    */ public class CategoriaProducto
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="categoria_producto", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_producto")
/*  22:    */   @Column(name="id_categoria_producto", unique=true, nullable=false)
/*  23:    */   private int idCategoriaProducto;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", nullable=false, length=10)
/*  29:    */   @NotNull
/*  30:    */   @Size(max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", nullable=false, length=50)
/*  33:    */   @NotNull
/*  34:    */   @Size(max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   
/*  44:    */   public CategoriaProducto() {}
/*  45:    */   
/*  46:    */   public CategoriaProducto(int idCategoriaProducto, String codigo, String nombre)
/*  47:    */   {
/*  48: 76 */     this.idCategoriaProducto = idCategoriaProducto;
/*  49: 77 */     this.codigo = codigo;
/*  50: 78 */     this.nombre = nombre;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 83 */     return this.idCategoriaProducto;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdCategoriaProducto()
/*  59:    */   {
/*  60: 87 */     return this.idCategoriaProducto;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdCategoriaProducto(int idCategoriaProducto)
/*  64:    */   {
/*  65: 91 */     this.idCategoriaProducto = idCategoriaProducto;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70: 95 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75: 99 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80:103 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:107 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getCodigo()
/*  89:    */   {
/*  90:111 */     return this.codigo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setCodigo(String codigo)
/*  94:    */   {
/*  95:115 */     this.codigo = codigo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getNombre()
/*  99:    */   {
/* 100:119 */     return this.nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNombre(String nombre)
/* 104:    */   {
/* 105:123 */     this.nombre = nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getDescripcion()
/* 109:    */   {
/* 110:127 */     return this.descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDescripcion(String descripcion)
/* 114:    */   {
/* 115:131 */     this.descripcion = descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean getActivo()
/* 119:    */   {
/* 120:135 */     return this.activo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setActivo(boolean activo)
/* 124:    */   {
/* 125:139 */     this.activo = activo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean getPredeterminado()
/* 129:    */   {
/* 130:143 */     return this.predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPredeterminado(boolean predeterminado)
/* 134:    */   {
/* 135:147 */     this.predeterminado = predeterminado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String toString()
/* 139:    */   {
/* 140:152 */     return this.nombre;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaProducto
 * JD-Core Version:    0.7.0.1
 */