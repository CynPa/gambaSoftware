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
/*  14:    */ @Table(name="motivo_nota_credito_proveedor", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class MotivoNotaCreditoProveedor
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -6592981785418453353L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="motivo_nota_credito_proveedor", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="motivo_nota_credito_proveedor")
/*  22:    */   @Column(name="id_motivo_nota_credito_proveedor")
/*  23:    */   private int idMotivoNotaCreditoProveedor;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", nullable=false, length=10)
/*  29:    */   @NotNull
/*  30:    */   @Size(min=2, max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", nullable=false, length=50)
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
/*  46: 87 */     return this.idMotivoNotaCreditoProveedor;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdMotivoNotaCreditoProveedor()
/*  50:    */   {
/*  51: 94 */     return this.idMotivoNotaCreditoProveedor;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdMotivoNotaCreditoProveedor(int idMotivoNotaCreditoProveedor)
/*  55:    */   {
/*  56:102 */     this.idMotivoNotaCreditoProveedor = idMotivoNotaCreditoProveedor;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdOrganizacion()
/*  60:    */   {
/*  61:109 */     return this.idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdOrganizacion(int idOrganizacion)
/*  65:    */   {
/*  66:117 */     this.idOrganizacion = idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdSucursal()
/*  70:    */   {
/*  71:124 */     return this.idSucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdSucursal(int idSucursal)
/*  75:    */   {
/*  76:132 */     this.idSucursal = idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getCodigo()
/*  80:    */   {
/*  81:139 */     return this.codigo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setCodigo(String codigo)
/*  85:    */   {
/*  86:147 */     this.codigo = codigo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getNombre()
/*  90:    */   {
/*  91:154 */     return this.nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setNombre(String nombre)
/*  95:    */   {
/*  96:162 */     this.nombre = nombre;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getDescripcion()
/* 100:    */   {
/* 101:169 */     return this.descripcion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setDescripcion(String descripcion)
/* 105:    */   {
/* 106:177 */     this.descripcion = descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isActivo()
/* 110:    */   {
/* 111:184 */     return this.activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setActivo(boolean activo)
/* 115:    */   {
/* 116:192 */     this.activo = activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isPredeterminado()
/* 120:    */   {
/* 121:199 */     return this.predeterminado;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setPredeterminado(boolean predeterminado)
/* 125:    */   {
/* 126:207 */     this.predeterminado = predeterminado;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MotivoNotaCreditoProveedor
 * JD-Core Version:    0.7.0.1
 */