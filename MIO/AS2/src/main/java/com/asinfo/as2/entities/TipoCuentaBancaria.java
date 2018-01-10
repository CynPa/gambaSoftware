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
/*  14:    */ @Table(name="tipo_cuenta_bancaria", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class TipoCuentaBancaria
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="tipo_cuenta_bancaria", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_cuenta_bancaria")
/*  22:    */   @Column(name="id_tipo_cuenta_bancaria", unique=true, nullable=false)
/*  23:    */   private int idTipoCuentaBancaria;
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
/*  34:    */   @Size(min=1, max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", nullable=true, length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 75 */     return this.idTipoCuentaBancaria;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdTipoCuentaBancaria()
/*  50:    */   {
/*  51: 84 */     return this.idTipoCuentaBancaria;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdTipoCuentaBancaria(int idTipoCuentaBancaria)
/*  55:    */   {
/*  56: 94 */     this.idTipoCuentaBancaria = idTipoCuentaBancaria;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdOrganizacion()
/*  60:    */   {
/*  61:103 */     return this.idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdOrganizacion(int idOrganizacion)
/*  65:    */   {
/*  66:113 */     this.idOrganizacion = idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdSucursal()
/*  70:    */   {
/*  71:122 */     return this.idSucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdSucursal(int idSucursal)
/*  75:    */   {
/*  76:132 */     this.idSucursal = idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getNombre()
/*  80:    */   {
/*  81:141 */     return this.nombre;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setNombre(String nombre)
/*  85:    */   {
/*  86:151 */     this.nombre = nombre;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getDescripcion()
/*  90:    */   {
/*  91:160 */     return this.descripcion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setDescripcion(String descripcion)
/*  95:    */   {
/*  96:170 */     this.descripcion = descripcion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isActivo()
/* 100:    */   {
/* 101:179 */     return this.activo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setActivo(boolean activo)
/* 105:    */   {
/* 106:189 */     this.activo = activo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isPredeterminado()
/* 110:    */   {
/* 111:198 */     return this.predeterminado;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setPredeterminado(boolean predeterminado)
/* 115:    */   {
/* 116:208 */     this.predeterminado = predeterminado;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getCodigo()
/* 120:    */   {
/* 121:217 */     return this.codigo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setCodigo(String codigo)
/* 125:    */   {
/* 126:227 */     this.codigo = codigo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String toString()
/* 130:    */   {
/* 131:232 */     return this.nombre;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoCuentaBancaria
 * JD-Core Version:    0.7.0.1
 */