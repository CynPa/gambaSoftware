/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="categoria_articulo_servicio")
/*  16:    */ public class CategoriaArticuloServicio
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -6264401803132998147L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="categoria_articulo_servicio", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_articulo_servicio")
/*  23:    */   @Column(name="id_categoria_articulo_servicio")
/*  24:    */   private int idCategoriaArticuloServicio;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="codigo", nullable=false, length=10)
/*  30:    */   @NotNull
/*  31:    */   @Size(min=2, max=10)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="nombre", nullable=false, length=50)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   
/*  45:    */   public int getIdCategoriaArticuloServicio()
/*  46:    */   {
/*  47: 85 */     return this.idCategoriaArticuloServicio;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setIdCategoriaArticuloServicio(int idCategoriaArticuloServicio)
/*  51:    */   {
/*  52: 95 */     this.idCategoriaArticuloServicio = idCategoriaArticuloServicio;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdOrganizacion()
/*  56:    */   {
/*  57:104 */     return this.idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdOrganizacion(int idOrganizacion)
/*  61:    */   {
/*  62:114 */     this.idOrganizacion = idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdSucursal()
/*  66:    */   {
/*  67:123 */     return this.idSucursal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdSucursal(int idSucursal)
/*  71:    */   {
/*  72:133 */     this.idSucursal = idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getNombre()
/*  76:    */   {
/*  77:142 */     return this.nombre;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setNombre(String nombre)
/*  81:    */   {
/*  82:152 */     this.nombre = nombre;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getDescripcion()
/*  86:    */   {
/*  87:161 */     return this.descripcion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setDescripcion(String descripcion)
/*  91:    */   {
/*  92:171 */     this.descripcion = descripcion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isActivo()
/*  96:    */   {
/*  97:180 */     return this.activo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setActivo(boolean activo)
/* 101:    */   {
/* 102:190 */     this.activo = activo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean isPredeterminado()
/* 106:    */   {
/* 107:199 */     return this.predeterminado;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setPredeterminado(boolean predeterminado)
/* 111:    */   {
/* 112:209 */     this.predeterminado = predeterminado;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getCodigo()
/* 116:    */   {
/* 117:218 */     return this.codigo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setCodigo(String codigo)
/* 121:    */   {
/* 122:228 */     this.codigo = codigo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getId()
/* 126:    */   {
/* 127:238 */     return this.idCategoriaArticuloServicio;
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.CategoriaArticuloServicio
 * JD-Core Version:    0.7.0.1
 */