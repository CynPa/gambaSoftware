/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ComprobanteSRICreditoTributarioSRI;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="credito_tributarioSRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo"})})
/*  24:    */ public class CreditoTributarioSRI
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="credito_tributarioSRI", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="credito_tributarioSRI")
/*  31:    */   @Column(name="id_credito_tributarioSRI")
/*  32:    */   private int idCreditoTributarioSRI;
/*  33:    */   @Column(name="codigo", length=10, nullable=false)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=10)
/*  36:    */   private String codigo;
/*  37:    */   @Column(name="nombre", length=50, nullable=false)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=2, max=200)
/*  40:    */   private String nombre;
/*  41:    */   @Column(name="descripcion", length=200)
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   @Column(name="predeterminado", nullable=false)
/*  47:    */   private boolean predeterminado;
/*  48:    */   @Temporal(TemporalType.DATE)
/*  49:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  50:    */   @NotNull
/*  51:    */   private Date fechaDesde;
/*  52:    */   @Temporal(TemporalType.DATE)
/*  53:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  54:    */   @NotNull
/*  55:    */   private Date fechaHasta;
/*  56:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="creditoTributarioSRI")
/*  57: 84 */   private List<ComprobanteSRICreditoTributarioSRI> listaComprobanteSRICreditoTributarioSRI = new ArrayList();
/*  58:    */   
/*  59:    */   public int getIdCreditoTributarioSRI()
/*  60:    */   {
/*  61: 93 */     return this.idCreditoTributarioSRI;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdCreditoTributarioSRI(int idCreditoTributarioSRI)
/*  65:    */   {
/*  66:103 */     this.idCreditoTributarioSRI = idCreditoTributarioSRI;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getCodigo()
/*  70:    */   {
/*  71:107 */     return this.codigo;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setCodigo(String codigo)
/*  75:    */   {
/*  76:111 */     this.codigo = codigo;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getNombre()
/*  80:    */   {
/*  81:115 */     return this.nombre;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setNombre(String nombre)
/*  85:    */   {
/*  86:119 */     this.nombre = nombre;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getDescripcion()
/*  90:    */   {
/*  91:123 */     return this.descripcion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setDescripcion(String descripcion)
/*  95:    */   {
/*  96:127 */     this.descripcion = descripcion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isActivo()
/* 100:    */   {
/* 101:131 */     return this.activo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setActivo(boolean activo)
/* 105:    */   {
/* 106:135 */     this.activo = activo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isPredeterminado()
/* 110:    */   {
/* 111:139 */     return this.predeterminado;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setPredeterminado(boolean predeterminado)
/* 115:    */   {
/* 116:143 */     this.predeterminado = predeterminado;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Date getFechaDesde()
/* 120:    */   {
/* 121:147 */     return this.fechaDesde;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setFechaDesde(Date fechaDesde)
/* 125:    */   {
/* 126:151 */     this.fechaDesde = fechaDesde;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Date getFechaHasta()
/* 130:    */   {
/* 131:155 */     return this.fechaHasta;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setFechaHasta(Date fechaHasta)
/* 135:    */   {
/* 136:159 */     this.fechaHasta = fechaHasta;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int getId()
/* 140:    */   {
/* 141:164 */     return getIdCreditoTributarioSRI();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String toString()
/* 145:    */   {
/* 146:169 */     return this.nombre;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<ComprobanteSRICreditoTributarioSRI> getListaComprobanteSRICreditoTributarioSRI()
/* 150:    */   {
/* 151:176 */     return this.listaComprobanteSRICreditoTributarioSRI;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setListaComprobanteSRICreditoTributarioSRI(List<ComprobanteSRICreditoTributarioSRI> listaComprobanteSRICreditoTributarioSRI)
/* 155:    */   {
/* 156:184 */     this.listaComprobanteSRICreditoTributarioSRI = listaComprobanteSRICreditoTributarioSRI;
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.CreditoTributarioSRI
 * JD-Core Version:    0.7.0.1
 */