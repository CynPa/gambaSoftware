/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="denominacion_forma_cobro")
/*  20:    */ public class DenominacionFormaCobro
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 4678701765469707314L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="denominacion_forma_cobro", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="denominacion_forma_cobro")
/*  27:    */   @Column(name="id_denominacion_forma_cobro")
/*  28:    */   private int idDenominacionFormaCobro;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   @NotNull
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=10)
/*  36:    */   @Size(min=1, max=10)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", nullable=false, length=50)
/*  39:    */   @Size(min=3, max=50)
/*  40:    */   @NotNull
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="descripcion", length=200, nullable=true)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="activo", nullable=false)
/*  46:    */   private boolean activo;
/*  47:    */   @Column(name="valor", nullable=false, precision=12, scale=2)
/*  48:    */   @Min(0L)
/*  49:    */   @NotNull
/*  50: 66 */   private BigDecimal valor = BigDecimal.ZERO;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_forma_pago", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private FormaPago formaPago;
/*  55:    */   
/*  56:    */   public int getId()
/*  57:    */   {
/*  58: 78 */     return this.idDenominacionFormaCobro;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdDenominacionFormaCobro()
/*  62:    */   {
/*  63: 82 */     return this.idDenominacionFormaCobro;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdDenominacionFormaCobro(int idDenominacionFormaCobro)
/*  67:    */   {
/*  68: 86 */     this.idDenominacionFormaCobro = idDenominacionFormaCobro;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdOrganizacion()
/*  72:    */   {
/*  73: 90 */     return this.idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdOrganizacion(int idOrganizacion)
/*  77:    */   {
/*  78: 94 */     this.idOrganizacion = idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdSucursal()
/*  82:    */   {
/*  83: 98 */     return this.idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdSucursal(int idSucursal)
/*  87:    */   {
/*  88:102 */     this.idSucursal = idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getCodigo()
/*  92:    */   {
/*  93:106 */     return this.codigo;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setCodigo(String codigo)
/*  97:    */   {
/*  98:110 */     this.codigo = codigo;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getNombre()
/* 102:    */   {
/* 103:114 */     return this.nombre;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setNombre(String nombre)
/* 107:    */   {
/* 108:118 */     this.nombre = nombre;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getDescripcion()
/* 112:    */   {
/* 113:122 */     return this.descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setDescripcion(String descripcion)
/* 117:    */   {
/* 118:126 */     this.descripcion = descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isActivo()
/* 122:    */   {
/* 123:130 */     return this.activo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setActivo(boolean activo)
/* 127:    */   {
/* 128:134 */     this.activo = activo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public BigDecimal getValor()
/* 132:    */   {
/* 133:138 */     return this.valor;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setValor(BigDecimal valor)
/* 137:    */   {
/* 138:142 */     this.valor = valor;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public FormaPago getFormaPago()
/* 142:    */   {
/* 143:146 */     return this.formaPago;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setFormaPago(FormaPago formaPago)
/* 147:    */   {
/* 148:150 */     this.formaPago = formaPago;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DenominacionFormaCobro
 * JD-Core Version:    0.7.0.1
 */