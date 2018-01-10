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
/*  14:    */ @Table(name="tipo_vendedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class TipoVendedor
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="tipo_vendedor", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_vendedor")
/*  22:    */   @Column(name="id_tipo_vendedor")
/*  23:    */   private int idTipoVendedor;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", length=10, nullable=false)
/*  29:    */   @NotNull
/*  30:    */   @Size(min=2, max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", length=50, nullable=false)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 94 */     return this.idTipoVendedor;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdTipoVendedor()
/*  50:    */   {
/*  51:102 */     return this.idTipoVendedor;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdTipoVendedor(int idTipoVendedor)
/*  55:    */   {
/*  56:110 */     this.idTipoVendedor = idTipoVendedor;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdOrganizacion()
/*  60:    */   {
/*  61:118 */     return this.idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdOrganizacion(int idOrganizacion)
/*  65:    */   {
/*  66:126 */     this.idOrganizacion = idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdSucursal()
/*  70:    */   {
/*  71:134 */     return this.idSucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdSucursal(int idSucursal)
/*  75:    */   {
/*  76:142 */     this.idSucursal = idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getCodigo()
/*  80:    */   {
/*  81:150 */     return this.codigo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setCodigo(String codigo)
/*  85:    */   {
/*  86:158 */     this.codigo = codigo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getNombre()
/*  90:    */   {
/*  91:166 */     return this.nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setNombre(String nombre)
/*  95:    */   {
/*  96:174 */     this.nombre = nombre;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getDescripcion()
/* 100:    */   {
/* 101:182 */     return this.descripcion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setDescripcion(String descripcion)
/* 105:    */   {
/* 106:190 */     this.descripcion = descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isActivo()
/* 110:    */   {
/* 111:198 */     return this.activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setActivo(boolean activo)
/* 115:    */   {
/* 116:206 */     this.activo = activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isPredeterminado()
/* 120:    */   {
/* 121:214 */     return this.predeterminado;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setPredeterminado(boolean predeterminado)
/* 125:    */   {
/* 126:222 */     this.predeterminado = predeterminado;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoVendedor
 * JD-Core Version:    0.7.0.1
 */