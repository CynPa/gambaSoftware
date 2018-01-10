/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.FetchType;
/*  7:   */ import javax.persistence.GeneratedValue;
/*  8:   */ import javax.persistence.GenerationType;
/*  9:   */ import javax.persistence.Id;
/* 10:   */ import javax.persistence.JoinColumn;
/* 11:   */ import javax.persistence.OneToOne;
/* 12:   */ import javax.persistence.Table;
/* 13:   */ import javax.persistence.TableGenerator;
/* 14:   */ import javax.validation.constraints.NotNull;
/* 15:   */ import javax.validation.constraints.Size;
/* 16:   */ 
/* 17:   */ @Entity
/* 18:   */ @Table(name="forma_pagoSRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_empresa", "codigo"})})
/* 19:   */ public class FormaPagoSRI
/* 20:   */   extends EntidadBase
/* 21:   */   implements Serializable
/* 22:   */ {
/* 23:   */   private static final long serialVersionUID = 1L;
/* 24:   */   @Id
/* 25:   */   @TableGenerator(name="forma_pagoSRI", initialValue=0, allocationSize=50)
/* 26:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="forma_pagoSRI")
/* 27:   */   @Column(name="id_forma_pagoSRI", unique=true, nullable=false)
/* 28:   */   private int idFormaPagoSRI;
/* 29:   */   @Column(name="id_organizacion", nullable=false)
/* 30:   */   private int idOrganizacion;
/* 31:   */   @Column(name="id_sucursal", nullable=false)
/* 32:   */   private int idSucursal;
/* 33:   */   @Column(name="codigo", length=10, nullable=false)
/* 34:   */   @Size(min=2, max=10)
/* 35:   */   @NotNull
/* 36:   */   private String codigo;
/* 37:   */   @OneToOne(fetch=FetchType.LAZY)
/* 38:   */   @JoinColumn(name="id_empresa")
/* 39:   */   private Empresa empresa;
/* 40:   */   
/* 41:   */   public int getId()
/* 42:   */   {
/* 43:58 */     return this.idFormaPagoSRI;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int getIdFormaPagoSRI()
/* 47:   */   {
/* 48:62 */     return this.idFormaPagoSRI;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setIdFormaPagoSRI(int idFormaPagoSRI)
/* 52:   */   {
/* 53:66 */     this.idFormaPagoSRI = idFormaPagoSRI;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int getIdOrganizacion()
/* 57:   */   {
/* 58:70 */     return this.idOrganizacion;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void setIdOrganizacion(int idOrganizacion)
/* 62:   */   {
/* 63:74 */     this.idOrganizacion = idOrganizacion;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int getIdSucursal()
/* 67:   */   {
/* 68:78 */     return this.idSucursal;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void setIdSucursal(int idSucursal)
/* 72:   */   {
/* 73:82 */     this.idSucursal = idSucursal;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public String getCodigo()
/* 77:   */   {
/* 78:86 */     return this.codigo;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public void setCodigo(String codigo)
/* 82:   */   {
/* 83:90 */     this.codigo = codigo;
/* 84:   */   }
/* 85:   */   
/* 86:   */   public Empresa getEmpresa()
/* 87:   */   {
/* 88:94 */     return this.empresa;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public void setEmpresa(Empresa empresa)
/* 92:   */   {
/* 93:98 */     this.empresa = empresa;
/* 94:   */   }
/* 95:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FormaPagoSRI
 * JD-Core Version:    0.7.0.1
 */