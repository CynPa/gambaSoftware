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
/*  14:    */ @Table(name="tipo_tarjeta_credito", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo", "id_organizacion"})})
/*  15:    */ public class TipoTarjetaCredito
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="tipo_tarjeta_credito", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_tarjeta_credito")
/*  22:    */   @Column(name="id_tipo_tarjeta_credito")
/*  23:    */   private int idTipoTarjetaCredito;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", length=10, nullable=false)
/*  29:    */   @NotNull
/*  30:    */   @Size(max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", length=50, nullable=false)
/*  33:    */   @NotNull
/*  34:    */   @Size(max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private boolean predeterminado;
/*  45:    */   
/*  46:    */   public int getId()
/*  47:    */   {
/*  48: 69 */     return this.idTipoTarjetaCredito;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdTipoTarjetaCredito()
/*  52:    */   {
/*  53: 73 */     return this.idTipoTarjetaCredito;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdTipoTarjetaCredito(int idTipoTarjetaCredito)
/*  57:    */   {
/*  58: 77 */     this.idTipoTarjetaCredito = idTipoTarjetaCredito;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdOrganizacion()
/*  62:    */   {
/*  63: 81 */     return this.idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdOrganizacion(int idOrganizacion)
/*  67:    */   {
/*  68: 85 */     this.idOrganizacion = idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdSucursal()
/*  72:    */   {
/*  73: 89 */     return this.idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdSucursal(int idSucursal)
/*  77:    */   {
/*  78: 93 */     this.idSucursal = idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getCodigo()
/*  82:    */   {
/*  83: 97 */     return this.codigo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setCodigo(String codigo)
/*  87:    */   {
/*  88:101 */     this.codigo = codigo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getNombre()
/*  92:    */   {
/*  93:105 */     return this.nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setNombre(String nombre)
/*  97:    */   {
/*  98:109 */     this.nombre = nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getDescripcion()
/* 102:    */   {
/* 103:113 */     return this.descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setDescripcion(String descripcion)
/* 107:    */   {
/* 108:117 */     this.descripcion = descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isActivo()
/* 112:    */   {
/* 113:121 */     return this.activo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setActivo(boolean activo)
/* 117:    */   {
/* 118:125 */     this.activo = activo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isPredeterminado()
/* 122:    */   {
/* 123:129 */     return this.predeterminado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPredeterminado(boolean predeterminado)
/* 127:    */   {
/* 128:133 */     this.predeterminado = predeterminado;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String toString()
/* 132:    */   {
/* 133:138 */     return this.nombre;
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoTarjetaCredito
 * JD-Core Version:    0.7.0.1
 */