/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
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
/*  15:    */ @Table(name="tipo_presentacion_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class TipoPresentacionProducto
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="tipo_presentacion_producto", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_presentacion_producto")
/*  24:    */   @Column(name="id_tipo_presentacion_producto", unique=true, nullable=false)
/*  25:    */   private int idTipoPresentacionProducto;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="codigo", nullable=false, length=10)
/*  31:    */   @NotNull
/*  32:    */   @Size(min=1, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=1, max=50)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="descripcion", nullable=true, length=300)
/*  39:    */   @Size(max=300)
/*  40:    */   private String descripcion;
/*  41:    */   @Column(name="activo", nullable=false)
/*  42:    */   @NotNull
/*  43: 62 */   private boolean activo = true;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private boolean predeterminado;
/*  47:    */   
/*  48:    */   public TipoPresentacionProducto() {}
/*  49:    */   
/*  50:    */   public TipoPresentacionProducto(int idTipoPresentacionProducto, String codigo, String nombre)
/*  51:    */   {
/*  52: 80 */     this.idTipoPresentacionProducto = idTipoPresentacionProducto;
/*  53: 81 */     this.codigo = codigo;
/*  54: 82 */     this.nombre = nombre;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getId()
/*  58:    */   {
/*  59: 87 */     return this.idTipoPresentacionProducto;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdTipoPresentacionProducto()
/*  63:    */   {
/*  64: 96 */     return this.idTipoPresentacionProducto;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdTipoPresentacionProducto(int idTipoPresentacionProducto)
/*  68:    */   {
/*  69:106 */     this.idTipoPresentacionProducto = idTipoPresentacionProducto;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdOrganizacion()
/*  73:    */   {
/*  74:115 */     return this.idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdOrganizacion(int idOrganizacion)
/*  78:    */   {
/*  79:125 */     this.idOrganizacion = idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdSucursal()
/*  83:    */   {
/*  84:134 */     return this.idSucursal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdSucursal(int idSucursal)
/*  88:    */   {
/*  89:144 */     this.idSucursal = idSucursal;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getCodigo()
/*  93:    */   {
/*  94:153 */     return this.codigo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setCodigo(String codigo)
/*  98:    */   {
/*  99:163 */     this.codigo = codigo;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getNombre()
/* 103:    */   {
/* 104:172 */     return this.nombre;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setNombre(String nombre)
/* 108:    */   {
/* 109:182 */     this.nombre = nombre;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getDescripcion()
/* 113:    */   {
/* 114:191 */     return this.descripcion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setDescripcion(String descripcion)
/* 118:    */   {
/* 119:201 */     this.descripcion = descripcion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isActivo()
/* 123:    */   {
/* 124:210 */     return this.activo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setActivo(boolean activo)
/* 128:    */   {
/* 129:220 */     this.activo = activo;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isPredeterminado()
/* 133:    */   {
/* 134:229 */     return this.predeterminado;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setPredeterminado(boolean predeterminado)
/* 138:    */   {
/* 139:239 */     this.predeterminado = predeterminado;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoPresentacionProducto
 * JD-Core Version:    0.7.0.1
 */