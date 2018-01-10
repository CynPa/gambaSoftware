/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_cobro", indexes={@javax.persistence.Index(name="ix_detalle_cobro_cuenta_por_cobrar", columnList="id_cuenta_por_cobrar")})
/*  22:    */ public class DetalleCobro
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_cobro", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_cobro")
/*  29:    */   @Column(name="id_detalle_cobro")
/*  30:    */   private int idDetalleCobro;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_cobro", nullable=true)
/*  37:    */   private Cobro cobro;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_cuenta_por_cobrar", nullable=false)
/*  40:    */   private CuentaPorCobrar cuentaPorCobrar;
/*  41:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  42:    */   @Digits(integer=12, fraction=2)
/*  43:    */   private BigDecimal valor;
/*  44:    */   @OneToMany(mappedBy="detalleCobro", fetch=FetchType.LAZY)
/*  45: 69 */   private List<DetalleCobroFormaCobro> listaDetalleCobroFormaCobro = new ArrayList();
/*  46:    */   @Transient
/*  47: 72 */   private BigDecimal valorProrrateado = BigDecimal.ZERO;
/*  48:    */   
/*  49:    */   public int getId()
/*  50:    */   {
/*  51: 80 */     return this.idDetalleCobro;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIdOrganizacion()
/*  55:    */   {
/*  56: 89 */     return this.idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdOrganizacion(int idOrganizacion)
/*  60:    */   {
/*  61: 99 */     this.idOrganizacion = idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdSucursal()
/*  65:    */   {
/*  66:108 */     return this.idSucursal;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdSucursal(int idSucursal)
/*  70:    */   {
/*  71:118 */     this.idSucursal = idSucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdDetalleCobro()
/*  75:    */   {
/*  76:122 */     return this.idDetalleCobro;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdDetalleCobro(int idDetalleCobro)
/*  80:    */   {
/*  81:126 */     this.idDetalleCobro = idDetalleCobro;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Cobro getCobro()
/*  85:    */   {
/*  86:130 */     return this.cobro;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setCobro(Cobro cobro)
/*  90:    */   {
/*  91:134 */     this.cobro = cobro;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public CuentaPorCobrar getCuentaPorCobrar()
/*  95:    */   {
/*  96:143 */     return this.cuentaPorCobrar;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setCuentaPorCobrar(CuentaPorCobrar cuentaPorCobrar)
/* 100:    */   {
/* 101:153 */     this.cuentaPorCobrar = cuentaPorCobrar;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public BigDecimal getValor()
/* 105:    */   {
/* 106:162 */     return this.valor;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setValor(BigDecimal valor)
/* 110:    */   {
/* 111:172 */     this.valor = valor;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public List<DetalleCobroFormaCobro> getListaDetalleCobroFormaCobro()
/* 115:    */   {
/* 116:176 */     return this.listaDetalleCobroFormaCobro;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setListaDetalleCobroFormaCobro(List<DetalleCobroFormaCobro> listaDetalleCobroFormaCobro)
/* 120:    */   {
/* 121:180 */     this.listaDetalleCobroFormaCobro = listaDetalleCobroFormaCobro;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public BigDecimal getValorProrrateado()
/* 125:    */   {
/* 126:184 */     return this.valorProrrateado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setValorProrrateado(BigDecimal valorProrrateado)
/* 130:    */   {
/* 131:188 */     this.valorProrrateado = valorProrrateado;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleCobro
 * JD-Core Version:    0.7.0.1
 */